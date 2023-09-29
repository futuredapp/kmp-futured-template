package app.futured.kmptemplate.app

import com.arkivanov.decompose.value.Value

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}