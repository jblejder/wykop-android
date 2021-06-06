package io.github.wykopmobilny.di.modules

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.wykopmobilny.api.UserTokenRefresher
import io.github.wykopmobilny.api.user.LoginApi
import io.github.wykopmobilny.base.WykopSchedulers
import io.github.wykopmobilny.ui.modules.Navigator
import io.github.wykopmobilny.ui.modules.NavigatorApi
import io.github.wykopmobilny.ui.modules.notifications.WykopNotificationManager
import io.github.wykopmobilny.ui.modules.notifications.WykopNotificationManagerApi
import io.github.wykopmobilny.utils.ClipboardHelper
import io.github.wykopmobilny.utils.ClipboardHelperApi
import io.github.wykopmobilny.utils.api.CredentialsPreferences
import io.github.wykopmobilny.utils.api.CredentialsPreferencesApi
import io.github.wykopmobilny.utils.preferences.BlacklistPreferences
import io.github.wykopmobilny.utils.preferences.BlacklistPreferencesApi
import io.github.wykopmobilny.utils.preferences.LinksPreferences
import io.github.wykopmobilny.utils.preferences.LinksPreferencesApi
import io.github.wykopmobilny.utils.preferences.SettingsPreferences
import io.github.wykopmobilny.utils.preferences.SettingsPreferencesApi
import io.github.wykopmobilny.utils.usermanager.UserManager
import io.github.wykopmobilny.utils.usermanager.UserManagerApi
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideWykopSchedulers(): io.github.wykopmobilny.base.Schedulers = WykopSchedulers()

    @Provides
    fun provideCredentialsPreferences(context: Context): CredentialsPreferencesApi =
        CredentialsPreferences(context)

    @Provides
    fun provideSettingsPreferences(context: Context): SettingsPreferencesApi =
        SettingsPreferences(context)

    @Provides
    fun provideLinksPreferencesApi(context: Context): LinksPreferencesApi =
        LinksPreferences(context)

    @Provides
    fun provideUserManagerApi(credentialsPreferencesApi: CredentialsPreferencesApi): UserManagerApi =
        UserManager(credentialsPreferencesApi)

    @Provides
    fun provideNotificationManager(context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Singleton
    fun provideWykopNotificationManager(mgr: NotificationManager): WykopNotificationManagerApi =
        WykopNotificationManager(mgr)

    @Provides
    fun provideUserTokenRefresher(userApi: LoginApi, userManagerApi: UserManagerApi) =
        UserTokenRefresher(userApi, userManagerApi)

    @Provides
    fun provideNavigatorApi(): NavigatorApi = Navigator()

    @Provides
    fun provideBlacklistApi(context: Context): BlacklistPreferencesApi = BlacklistPreferences(context)

    @Provides
    fun provideClipboardHelper(context: Context): ClipboardHelperApi = ClipboardHelper(context)
}
