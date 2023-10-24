package app.futured.kmptemplate.network.graphql.result

sealed class CloudErrorCode {
    data object Unauthorized : CloudErrorCode()
    data object NotFound : CloudErrorCode()
    data class Unknown(val extensionCode: String?) : CloudErrorCode()

    companion object {
        fun fromExtensionCode(code: String?): CloudErrorCode = when (code) {
            "UNAUTHORIZED" -> Unauthorized
            "UNAUTHENTICATED" -> Unauthorized
            "NOT_FOUND" -> NotFound
            else -> Unknown(extensionCode = code)
        }
    }
}
