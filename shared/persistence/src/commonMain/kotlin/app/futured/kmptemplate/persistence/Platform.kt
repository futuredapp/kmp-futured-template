package app.futured.kmptemplate.persistence

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
