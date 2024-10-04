package com.rudolfhladik.componentprocessor.content

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class ComponentPoetGenerator(
    private val logger: KSPLogger,
) { // TODO cant access classes from other modules
    fun tryPoet(
        viewModelExtPackage: String,
        component: KSClassDeclaration,
        codeGenerator: CodeGenerator,
    ) {
        val baseName = component.qualifiedName?.asString()?.removeSuffix("Screen") ?: error("Unable to get base name")
        val classname = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(baseName.plus("Component")),
        )

        val appComponentContext = ClassName(
            packageName = component.packageName.asString(), // todo get from args
            simpleNames = listOf("AppComponentContext"),
        )

        val properties = component.getAllProperties()

        properties.forEach {
            logger.warn("${it.simpleName.getShortName()}")
            val type = it.type.toTypeName()
            logger.warn("$type")
        }

        val viewStateType = properties
            .find { it.type.toTypeName().toString().contains("ViewState") }
            ?: error("Cannot find ViewState type")

        val actionsType = properties
            .find { it.type.toTypeName().toString().contains("Actions") }
            ?: error("Cannot find ViewState type")

        logger.warn("$viewStateType")


        val VMClass = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(baseName.plus("ViewModel")),
        )

        val helloWorld = TypeSpec.classBuilder("HelloWorld")
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("componentContext", appComponentContext)
                    .build(),
            )
            .addSuperinterface(
                delegate = CodeBlock.builder().addStatement("componentContext").build(),
                superinterface = appComponentContext,
            )
            .addSuperinterface(
                component.asType(emptyList()).toTypeName(),
            )
            .addProperty(
                PropertySpec.builder("viewModel", VMClass)
                    .delegate(
                        CodeBlock.builder()
                            .addStatement("viewModel()").build(),
                    )
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
            )
        helloWorld
            .addProperty(
                PropertySpec.builder(viewStateType.simpleName.getShortName(), viewStateType.type.toTypeName())
                    .initializer(CodeBlock.builder().addStatement("viewModel.viewState").build())
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )

        helloWorld
            .addProperty(
                PropertySpec.builder(actionsType.simpleName.getShortName(), actionsType.type.toTypeName())
                    .initializer(CodeBlock.builder().addStatement("viewModel").build())
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )

            .build()

        val spec = FileSpec.builder(classname)
            .addImport(viewModelExtPackage.removeSuffix("viewModel"), "viewModel")
            .addType(helloWorld.build())
            .build()


        spec.writeTo(codeGenerator, aggregating = true)
    }
}
