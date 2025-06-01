package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.model.mapping.toUiModel
import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import app.futured.kmptemplate.network.rest.api.RembrandApi
import app.futured.kmptemplate.network.rest.result.getOrThrow
import org.koin.core.annotation.Factory

@Factory
internal class GetStylesUseCase(
    private val rembrandAp: RembrandApi,
) : UseCase<Unit, List<AvatarStyle>>() {

    override suspend fun build(args: Unit): List<AvatarStyle> {
        return rembrandAp.getAvatarStyles().getOrThrow().data
            .map { it.toUiModel() }
    }
}

