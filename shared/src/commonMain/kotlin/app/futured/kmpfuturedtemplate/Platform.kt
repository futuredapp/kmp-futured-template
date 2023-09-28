package app.futured.kmpfuturedtemplate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform