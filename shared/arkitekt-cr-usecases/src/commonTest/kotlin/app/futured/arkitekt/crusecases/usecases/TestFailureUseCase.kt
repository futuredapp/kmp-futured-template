package app.futured.arkitekt.crusecases.usecases

import app.futured.arkitekt.crusecases.UseCase

class TestFailureUseCase : UseCase<Throwable, Unit> {

    override suspend fun build(args: Throwable): Unit = throw args
}
