package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.network.rest.api.RembrandApi
import app.futured.kmptemplate.network.rest.dto.request.GenerateAvatarRequest
import app.futured.kmptemplate.network.rest.result.getOrThrow
import org.koin.core.annotation.Factory

@Factory
internal class GenerateAvatarUseCase(
    private val rembrandApi: RembrandApi,
) : UseCase<GenerateAvatarUseCase.Args, Unit>() {

    override suspend fun build(args: Args): Unit {
        val srcUrl = rembrandApi.getAvatarSrc().getOrThrow().data.last()

        rembrandApi.generateAvatar(
            GenerateAvatarRequest(
                faceImageUrl = srcUrl,
                poseImageUrl = srcUrl,
                styleId = args.styleId,
            ),
        ).getOrThrow().data
    }

    class Args(val styleId: Long)
}
