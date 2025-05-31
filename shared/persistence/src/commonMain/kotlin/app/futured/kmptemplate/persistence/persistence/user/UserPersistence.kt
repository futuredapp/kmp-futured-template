package app.futured.kmptemplate.persistence.persistence.user

import androidx.datastore.preferences.core.stringPreferencesKey
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import kotlinx.coroutines.flow.Flow


interface UserPersistence {

    fun getPhotoUrl(): Flow<String?>
    suspend fun savePhotoUrl(photoUrl: String)
}

internal class UserPersistenceImpl(
    private val primitivePersistence: PrimitivePersistence,
) : UserPersistence {

    private companion object {
        val PHOTO_URL_KEY = stringPreferencesKey("photo_url")
    }

    override fun getPhotoUrl(): Flow<String?> = primitivePersistence.observe(PHOTO_URL_KEY)

    override suspend fun savePhotoUrl(photoUrl: String) {
        primitivePersistence.save(PHOTO_URL_KEY, photoUrl)
    }
}

