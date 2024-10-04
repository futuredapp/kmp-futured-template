package app.futured.kmptemplate.util.domain.usecases

import app.futured.kmptemplate.util.domain.UseCase
import kotlinx.coroutines.delay

class TestUseCase : UseCase<Int, Int>() {

    override suspend fun build(args: Int): Int {
        delay(1000)
        return args
    }
}
