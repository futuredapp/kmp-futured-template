package com.rudolfhladik.componentprocessor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ComponentProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ComponentProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
            args = environment.options
        )
    }
}
