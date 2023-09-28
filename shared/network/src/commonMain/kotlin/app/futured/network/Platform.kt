package app.futured.network

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform