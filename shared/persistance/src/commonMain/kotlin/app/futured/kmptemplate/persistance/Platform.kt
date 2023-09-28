package app.futured.kmptemplate.persistance

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform