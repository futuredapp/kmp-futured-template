package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.model.mapping.toUiModel
import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.network.rest.api.RembrandApi
import app.futured.kmptemplate.network.rest.result.getOrThrow
import org.koin.core.annotation.Factory

@Factory
internal class GetAvatarsUseCase(
    private val rembrandApi: RembrandApi,
) : UseCase<Unit, List<Avatar>>() {

    override suspend fun build(args: Unit): List<Avatar> {
        val styles = rembrandApi.getAvatarStyles().getOrThrow().data.map { it.toUiModel() }
        return rembrandApi.getAvatars().getOrThrow().data.mapNotNull { avatar ->
            styles.find { it.id==avatar.styleId }?.let { style ->
                avatar.toUiModel(style)
            }
        }.sortedByDescending { it.id }
    }
}
