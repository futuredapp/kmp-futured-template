package com.rudolfhladik.componentprocessor.content

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.rudolfhladik.annotation.Component
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

internal class ComponentPoetGenerator(
    private val logger: KSPLogger,
) {
    fun tryPoet(
        appComponentContextPackage: String,
        viewModelExtPackage: String,
        component: KSClassDeclaration,
        codeGenerator: CodeGenerator,
    ) {
        val annotation: KSAnnotation = component.annotations.first {
            it.shortName.asString() == Component::class.simpleName
        }
        // Getting the 'argType' argument object from the @Component.
        val arg: KSType? = annotation.arguments.first().value as? KSType

        ///
        val baseName = component.qualifiedName?.asString()?.removeSuffix("Screen") ?: error("Unable to get base name")
        val classname = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(baseName.plus("Component")),
        )

        val appComponentContext = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf("AppComponentContext"),
        )

        val properties = component.getAllProperties()

        val viewStateType = properties
            .find { it.type.toTypeName().toString().contains("ViewState") }

        val actionsType = properties
            .find { it.type.toTypeName().toString().contains("Actions") }

        logger.warn("$viewStateType")


        val vMClass = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(baseName.plus("ViewModel")),
        )
        val constructorBuilder = FunSpec.constructorBuilder()
        if (arg != null) {
            constructorBuilder.addParameter("arg", (arg.declaration as KSClassDeclaration).asStarProjectedType().toTypeName())
        }

        constructorBuilder.addParameter("componentContext", appComponentContext)

        val vmStatement = if (arg != null) {
            "viewModel { parametersOf(arg) }"
        } else {
            "viewModel()"
        }

        val componentBuilder = TypeSpec.classBuilder(baseName.substringAfterLast('.').plus("Component"))
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(constructorBuilder.build())
            .addSuperinterface(
                delegate = CodeBlock.builder().addStatement("componentContext").build(),
                superinterface = appComponentContext,
            )
            .addSuperinterface(
                component.asType(emptyList()).toTypeName(),
            )
            .addProperty(
                PropertySpec.builder("viewModel", vMClass)
                    .delegate(
                        CodeBlock.builder()
                            .addStatement(vmStatement).build(),
                    )
                    .addModifiers(KModifier.PRIVATE)
                    .build(),
            )

        if (viewStateType != null) {
            componentBuilder
                .addProperty(
                    PropertySpec.builder(viewStateType.simpleName.getShortName(), viewStateType.type.toTypeName())
                        .initializer(CodeBlock.builder().addStatement("viewModel.viewState").build())
                        .addModifiers(KModifier.OVERRIDE)
                        .build(),
                )
        }

        if (actionsType != null) {
            componentBuilder
                .addProperty(
                    PropertySpec.builder(actionsType.simpleName.getShortName(), actionsType.type.toTypeName())
                        .initializer(CodeBlock.builder().addStatement("viewModel").build())
                        .addModifiers(KModifier.OVERRIDE)
                        .build(),
                )
        }

        val spec = FileSpec.builder(classname)
        if (arg != null) {
            spec.addImport("org.koin.core.parameter", "parametersOf")
        }
        spec.addImport(viewModelExtPackage.removeSuffix("viewModel"), "viewModel")
            .addImport(appComponentContextPackage.removeSuffix("AppComponentContext"), "AppComponentContext")
            .addType(componentBuilder.build())

        spec.build().writeTo(codeGenerator, aggregating = true)
    }
}
