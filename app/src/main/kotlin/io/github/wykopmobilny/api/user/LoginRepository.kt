package io.github.wykopmobilny.api.user

import io.github.wykopmobilny.api.endpoints.LoginRetrofitApi
import io.github.wykopmobilny.api.errorhandler.ErrorHandlerTransformer
import io.github.wykopmobilny.utils.api.CredentialsPreferencesApi
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val tagApi: LoginRetrofitApi,
    private val apiPreferences: CredentialsPreferencesApi
) : LoginApi {

    override fun getUserSessionToken() =
        tagApi.getUserSessionToken(apiPreferences.login!!, apiPreferences.userKey!!)
            .compose(ErrorHandlerTransformer())
}
