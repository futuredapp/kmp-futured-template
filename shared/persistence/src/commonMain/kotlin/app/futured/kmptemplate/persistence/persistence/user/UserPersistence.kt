package app.futured.kmptemplate.persistence.persistence.user

import androidx.datastore.preferences.core.booleanPreferencesKey
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import org.koin.core.annotation.Single

interface UserPersistence {
    suspend fun isUserLoggedIn(): Boolean

    suspend fun setUserLoggedIn(isLoggedIn: Boolean)
}

@Single
internal class UserPersistenceImpl(private val primitivePersistence: PrimitivePersistence) : UserPersistence {

    private companion object {
        val IS_USER_LOGGED_IN_KEY = booleanPreferencesKey("IS_USER_LOGGED_IN")
    }

    override suspend fun isUserLoggedIn(): Boolean = primitivePersistence.get(IS_USER_LOGGED_IN_KEY) ?: false

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) {
        primitivePersistence.save(IS_USER_LOGGED_IN_KEY, isLoggedIn)
    }
}
