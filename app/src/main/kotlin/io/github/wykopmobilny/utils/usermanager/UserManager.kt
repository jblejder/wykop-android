package io.github.wykopmobilny.utils.usermanager

import android.content.Context
import io.github.wykopmobilny.api.responses.LoginResponse
import io.github.wykopmobilny.storage.api.CredentialsPreferencesApi
import io.github.wykopmobilny.ui.dialogs.userNotLoggedInDialog

data class LoginCredentials(val login: String, val token: String)

data class UserCredentials(val login: String, val avatarUrl: String, val backgroundUrl: String?, val userKey: String)

interface SimpleUserManagerApi {
    fun isUserAuthorized(): Boolean
    fun getUserCredentials(): UserCredentials?
}

interface UserManagerApi : SimpleUserManagerApi {
    fun loginUser(credentials: LoginCredentials)
    fun logoutUser()
    fun saveCredentials(credentials: LoginResponse)
    fun runIfLoggedIn(context: Context, callback: () -> Unit)
}

class UserManager(private val credentialsPreferencesApi: CredentialsPreferencesApi) : UserManagerApi {
    override fun loginUser(credentials: LoginCredentials) {
        credentialsPreferencesApi.apply {
            login = credentials.login
            userKey = credentials.token
        }
    }

    override fun logoutUser() {
        credentialsPreferencesApi.apply {
            login = ""
            userToken = ""
            userKey = ""
        }
    }

    override fun saveCredentials(credentials: LoginResponse) {
        credentialsPreferencesApi.apply {
            avatarUrl = credentials.profile.avatar
            backgroundUrl = credentials.profile.background
            userToken = credentials.userkey
        }
    }

    override fun isUserAuthorized(): Boolean {
        credentialsPreferencesApi.run {
            return !login.isNullOrBlank() and !userKey.isNullOrBlank()
        }
    }

    override fun getUserCredentials(): UserCredentials? =
        credentialsPreferencesApi.run {
            if (!login.isNullOrEmpty() || !avatarUrl.isNullOrEmpty() || !userToken.isNullOrEmpty()) {
                UserCredentials(login!!, avatarUrl!!, backgroundUrl, userToken!!)
            } else {
                null
            }
        }

    override fun runIfLoggedIn(context: Context, callback: () -> Unit) {
        if (isUserAuthorized()) {
            callback.invoke()
        } else {
            userNotLoggedInDialog(context)?.show()
        }
    }
}
