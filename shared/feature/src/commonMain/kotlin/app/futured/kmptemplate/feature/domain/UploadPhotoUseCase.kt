package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.network.rest.api.RembrandApi
import app.futured.kmptemplate.network.rest.dto.request.uploadAvatarPartData
import app.futured.kmptemplate.network.rest.result.getOrThrow
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import org.koin.core.annotation.Factory

@Factory
internal class UploadPhotoUseCase(
    private val rembrandApi: RembrandApi,
    private val userPersistence: UserPersistence,
) : UseCase<UploadPhotoUseCase.Args, String>() {

    override suspend fun build(args: Args): String {
        val photos = rembrandApi.uploadAvatarSrc(
            uploadAvatarPartData(filename = args.filename, args.image),
        ).getOrThrow().data

        userPersistence.savePhotoUrl(photos.last())

        return photos.last()
    }

    class Args(val filename: String, val image: ByteArray)
}
