package app.futured.factorygenerator.processor

import app.futured.factorygenerator.annotation.GenerateFactory
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlin.math.log
import kotlin.reflect.KClass

class ComponentFactoryProcessor(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val components: Sequence<KSClassDeclaration> = resolver.findAnnotationsForClass(GenerateFactory::class)

        components.forEach { generateComponent(it) }

        return emptyList()
    }

    private fun generateComponent(component: KSClassDeclaration, logger: KSPLogger) =
        PoetFactoryComponentGenerator.generateFactory(component, codeGenerator)

    private fun Resolver.findAnnotationsForClass(kClass: KClass<*>): Sequence<KSClassDeclaration> =
        this.getSymbolsWithAnnotation(kClass.qualifiedName.toString())
            .filterIsInstance<KSClassDeclaration>()
}

