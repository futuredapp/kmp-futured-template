package com.rudolfhladik.componentprocessor.content

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class ComponentPoetGenerator { // TODO cant access classes from other modules
    private fun tryPoet(component: KSClassDeclaration, codeGenerator: CodeGenerator) {
        val classname = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(
                component.qualifiedName?.asString()?.removeSuffix("Screen")?.plus("Component")
                    ?: "Error",
            ),
        )

        val baseVMClass = ClassName(
            packageName = component.packageName.asString(),
            simpleNames = listOf(
                "BaseViewModel",
            ),
        )

        val superType = TypeSpec.classBuilder(baseVMClass)
            .build()

        val helloWorld = TypeSpec.classBuilder("HelloWorld")
            .addModifiers(KModifier.INTERNAL)
//            .addModifiers()
//            .superclass(resolveSuper)
//            .addSuperclassConstructorParameter(
//                CodeBlock.builder().addStatement()
//            )
            .addSuperinterface(
                component.asType(emptyList()).toTypeName(),
            )
            .addProperty(
                PropertySpec.builder("viewModel", String::class)
                    .delegate(CodeBlock.builder().addStatement("viewModel()").build())
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )
            .addProperty(
                PropertySpec.builder("viewState", String::class)
                    .initializer(CodeBlock.builder().addStatement("viewModel.viewState").build())
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )
            .build()

        val spec = FileSpec.builder(classname)
            .addType(helloWorld)
            .build()


        spec.writeTo(codeGenerator, aggregating = true)
    }
}
