package app.futured.kmptemplate.feature

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform