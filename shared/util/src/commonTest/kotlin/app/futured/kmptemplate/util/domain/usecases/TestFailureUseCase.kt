package app.futured.kmptemplate.util.domain.usecases

import app.futured.kmptemplate.util.domain.UseCase

class TestFailureUseCase : UseCase<Throwable, Unit>() {

    override suspend fun build(args: Throwable) {
        throw args
    }
}
