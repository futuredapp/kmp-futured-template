package app.futured.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform