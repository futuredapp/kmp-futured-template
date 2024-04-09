package com.rudolfhladik.componentprocessor.content

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.rudolfhladik.annotation.Component

internal class ComponentContentGenerator {
    internal companion object {
        private const val viewStateName = "viewState"
        private const val actionsName = "Actions"
        private const val suspendActionsName = "SuspendActions"
        private const val eventsName = "events"
    }

    fun generateContents(component: KSClassDeclaration, fileName: String, args: Map<String, String>): List<String> {
        val annotation: KSAnnotation = component.annotations.first {
            it.shortName.asString() == Component::class.simpleName
        }
        // Getting the 'argType' argument object from the @Component.
        val nameArgument: String? = annotation.arguments
            .firstOrNull { arg -> arg.name?.asString() == "argType" }
            ?.value?.toString()


        val contents = mutableListOf<String>()

        val packageLine = "package ${component.packageName.asString()}\n\n"
        contents.add(packageLine)

        val imports = getImports(component, nameArgument, args)
        contents.addAll(imports)

        val classContents = getClassAndBody(component, fileName, nameArgument)
        classContents.forEach {
            contents.add(it)
        }

        return contents
    }

    fun getFileName(component: KSClassDeclaration): String =
        getComponentName(component).plus("Component")

    private fun getClassAndBody(component: KSClassDeclaration, fileName: String, nameArgument: String?): List<String> {
        val classContents = mutableListOf<String>()

        val declarations = component.declarations
            .map { it.simpleName.asString() }

        val componentName = getComponentName(component)
        val screenInterfaceName = getScreenInterfaceName(component)

        val classLine = "internal class $fileName(\n"
        val argsLine = nameArgument?.let { "    arg: $nameArgument,\n" }
        val constructorParams = "    componentContext: ComponentContext,\n"
        val superTypeLine =
            ") : ViewModelComponent<${componentName}ViewModel>(componentContext), $screenInterfaceName {\n"
        val endClassLine = "}\n"

        val parametersForVM = nameArgument?.let { "parameters = { parametersOf(arg) }" } ?: ""
        val bodyViewModel =
            "    override val viewModel: ${componentName}ViewModel by viewModel($parametersForVM)\n"

        val bodyViewState = "    override val viewState: StateFlow<${componentName}ViewState> = viewModel.viewState\n"

        val bodyEvents = "    override val events: Flow<${componentName}Event> = viewModel.uiEvents\n"

        val bodyActions = "    override val actions: ${screenInterfaceName}.$actionsName = viewModel\n"

        val bodySuspendActions =
            "    override val suspendActions: ${screenInterfaceName}.$suspendActionsName = viewModel\n"

        classContents.addAll(listOfNotNull(classLine, argsLine, constructorParams, superTypeLine))

        classContents.add(bodyViewModel)
        if (declarations.any { it == viewStateName }) {
            classContents.add(bodyViewState)
        }
        if (declarations.any { it == eventsName }) {
            classContents.add(bodyEvents)
        }
        if (declarations.any { it == actionsName }) {
            classContents.add(bodyActions)
        }
        if (declarations.any { it == suspendActionsName }) {
            classContents.add(bodySuspendActions)
        }

        classContents.add(endClassLine)

        return classContents
    }

    private fun getImports(component: KSClassDeclaration, nameArgument: String?, args: Map<String, String>): List<String> {
        val declarations = component.declarations
            .map { it.simpleName.asString() }

        val viewModelComponentPackage = args.get("viewModel") ?: error("specify ViewModelComponent path")
        val viewModelExtPackage = args.get("viewModelExt") ?: error("specify viewModel extension path")
        val viewModelComponentImport = "import $viewModelComponentPackage\n"
        val viewModelExtImport = "import $viewModelExtPackage\n"

        val componentContextImport = "import com.arkivanov.decompose.ComponentContext\n"
        val flowImport = if (declarations.any { it == eventsName }) {
            "import kotlinx.coroutines.flow.Flow\n"
        } else null
        val stateFlowImport = if (declarations.any { it == viewStateName }) {
            "import kotlinx.coroutines.flow.StateFlow\n"
        } else null
        val parametersOfImport = if (nameArgument != null) "import org.koin.core.parameter.parametersOf\n" else ""

        return listOfNotNull(
            componentContextImport,
            flowImport,
            stateFlowImport,
            viewModelComponentImport,
            viewModelExtImport,
            parametersOfImport,
            "\n",
            "\n",
        )
    }

    private fun getComponentName(component: KSClassDeclaration) =
        getScreenInterfaceName(component)
            .removeSuffix("Screen")

    private fun getScreenInterfaceName(component: KSClassDeclaration) = component
        .qualifiedName
        ?.asString()
        ?.substringAfterLast(".") ?: "Error"
}
