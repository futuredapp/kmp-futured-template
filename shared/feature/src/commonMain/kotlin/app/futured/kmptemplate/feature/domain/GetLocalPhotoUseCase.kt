package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class GetLocalPhotoUseCase(
    private val userPersistence: UserPersistence,
) : FlowUseCase<Unit, String?>() {


    override fun build(args: Unit): Flow<String?> = userPersistence.getPhotoUrl()
}

