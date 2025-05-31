package app.futured.kmptemplate.network.rest.dto.request

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

fun uploadAvatarPartData(filename: String, photo: ByteArray): MultiPartFormDataContent {
    return MultiPartFormDataContent(
        formData {
            append(
                "photo",
                photo,
                Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=$filename")
                },
            )
        },
    )
}
