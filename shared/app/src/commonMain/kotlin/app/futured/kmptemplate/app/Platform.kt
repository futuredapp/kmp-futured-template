package app.futured.kmptemplate.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform