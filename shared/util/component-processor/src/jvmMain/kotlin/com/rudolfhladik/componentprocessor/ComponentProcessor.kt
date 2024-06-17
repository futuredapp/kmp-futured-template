package com.rudolfhladik.componentprocessor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.rudolfhladik.annotation.Component
import com.rudolfhladik.componentprocessor.content.ComponentContentGenerator
import java.io.OutputStream
import kotlin.reflect.KClass

class ComponentProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val args: Map<String, String>,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val components: Sequence<KSClassDeclaration> = resolver.findAnnotationsForClass(Component::class)

        components.forEach {
            generateComponent(it)
        }

        return emptyList()
    }

    private fun generateComponent(component: KSClassDeclaration) {
        val componentContentGenerator = ComponentContentGenerator()
        val fileName = componentContentGenerator.getFileName(component)

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = component.packageName.asString(),
            fileName = fileName,
        )

        val contents = componentContentGenerator.generateContents(component, fileName, args)

        file.use {
            it.writeAll(contents)
        }
    }

    private fun OutputStream.writeAll(contents: List<String>) {
        contents.forEach { content ->
            this.write(content.toByteArray())
        }
    }

    private fun Resolver.findAnnotationsForClass(kClass: KClass<*>): Sequence<KSClassDeclaration> =
        this.getSymbolsWithAnnotation(kClass.qualifiedName.toString())
            .filterIsInstance<KSClassDeclaration>()
}
