package app.futured.kmptemplate.network.rest.api

import app.futured.kmptemplate.network.rest.dto.request.GenerateAvatarRequest
import app.futured.kmptemplate.network.rest.dto.response.AvatarResponseWrapper
import app.futured.kmptemplate.network.rest.dto.response.SrcResponse
import app.futured.kmptemplate.network.rest.dto.response.StyleResponseWrapper
import app.futured.kmptemplate.network.rest.result.NetworkResult
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.request.forms.MultiPartFormDataContent


interface RembrandApi {
    @GET("avatar/src")
    suspend fun getAvatarSrc(): NetworkResult<SrcResponse>

    @POST("avatar/src")
    suspend fun uploadAvatarSrc(@Body map: MultiPartFormDataContent): NetworkResult<SrcResponse>

    @GET("avatar")
    suspend fun getAvatars(): NetworkResult<AvatarResponseWrapper>

    @Headers("Content-Type: application/json")
    @POST("avatar")
    suspend fun generateAvatar(@Body request: GenerateAvatarRequest): NetworkResult<AvatarResponseWrapper>

    @GET("avatar/styles")
    suspend fun getAvatarStyles(): NetworkResult<StyleResponseWrapper>

}
