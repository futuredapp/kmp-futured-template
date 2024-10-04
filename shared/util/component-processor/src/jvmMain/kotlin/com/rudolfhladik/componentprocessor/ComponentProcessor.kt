package com.rudolfhladik.componentprocessor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.rudolfhladik.annotation.Component
import com.rudolfhladik.componentprocessor.content.ComponentPoetGenerator
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
        val appComponentContextPackage = args.get("appComponentContext") ?: error("specify ViewModelComponent path")
        val viewModelExtPackage = args.get("viewModelExt") ?: error("specify viewModel extension path")

        // don't use old hard coded generation
//        val componentContentGenerator = ComponentContentGenerator()
//        componentContentGenerator.generateContents(component, args, codeGenerator)

        // use poet generation
        val poet = ComponentPoetGenerator(logger)
        poet.tryPoet(appComponentContextPackage, viewModelExtPackage, component, codeGenerator)
    }

    private fun Resolver.findAnnotationsForClass(kClass: KClass<*>): Sequence<KSClassDeclaration> =
        this.getSymbolsWithAnnotation(kClass.qualifiedName.toString())
            .filterIsInstance<KSClassDeclaration>()
}
