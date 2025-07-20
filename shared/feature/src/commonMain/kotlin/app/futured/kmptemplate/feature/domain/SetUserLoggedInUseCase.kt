package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import org.koin.core.annotation.Factory

@Factory
class SetUserLoggedInUseCase(private val userPersistence: UserPersistence) : UseCase<SetUserLoggedInUseCase.Args, Unit>() {

    override suspend fun build(args: Args) = userPersistence.setUserLoggedIn(args.isLoggedIn)

    data class Args(val isLoggedIn: Boolean)
}
