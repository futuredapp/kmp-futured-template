package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.network.rest.api.RembrandApi
import app.futured.kmptemplate.network.rest.result.getOrThrow
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import org.koin.core.annotation.Factory


@Factory
internal class LoadPhotoUseCase(
    private val rembrandApi: RembrandApi,
    private val userPersistence: UserPersistence,
) : UseCase<Unit, String?>() {

    override suspend fun build(args: Unit): String? {
        val photos = rembrandApi.getAvatarSrc().getOrThrow()
        val res = photos.lastOrNull()?.run {
            userPersistence.savePhotoUrl(this)
            this
        }

        return res
    }

}

