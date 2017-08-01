package io.github.feelfreelinux.wykopmobilny.utils

import android.content.Context
import io.github.feelfreelinux.wykopmobilny.glide.GlideApp
import io.github.feelfreelinux.wykopmobilny.objects.*
import org.json.JSONArray
import org.json.JSONObject

class WykopApiManager(context: Context) {
    val apiPrefs = ApiPreferences()
    val login = apiPrefs.login!!
    val accountKey = apiPrefs.userKey!!
    var networkUtils: NetworkUtils = NetworkUtils(context)

    fun getUserSessionToken(successCallback: (Any) -> Unit) {
        val params = ArrayList<Pair<String, String>>()
        params.add(Pair("accountkey", accountKey))
        params.add(Pair("login", login))

        networkUtils.sendPost("user/login", null, params, UserSessionReponse::class.java, successCallback, {})
    }

    fun getTagEntries(page: Int, tag: String, successCallback: (Any) -> Unit) =
        networkUtils.sendGet("tag/entries/$tag", "page/$page", TagFeedEntries::class.java, successCallback, {})

    fun getNotificationCount(successCallback: (Any) -> Unit) =
        networkUtils.sendGet("mywykop/NotificationsCount", null, NotificationCountResponse::class.java, successCallback, {})

    fun getHashTagsNotificationsCount(successCallback: (Any) -> Unit) =
        networkUtils.sendGet("mywykop/HashTagsNotificationsCount", null, NotificationCountResponse::class.java, successCallback, {})

    fun getMikroblogHot(page : Int, period : String, successCallback: (Any) -> Unit) =
        networkUtils.sendGet("stream/hot", "page/$page/period/$period", Array<SingleEntry>::class.java, successCallback, {})

    fun getMikroblogIndex(page : Int, successCallback: (Any) -> Unit) =
        networkUtils.sendGet("stream/index", "page/$page", Array<SingleEntry>::class.java, successCallback, {})

    fun getEntryIndex(id : Int, successCallback: (Any) -> Unit) =
        networkUtils.sendGet("entries/index", "$id", EntryDetails::class.java,  successCallback, {})

    fun voteEntry(entryId : Int, commentId : Int?, successCallback: (Any) -> Unit) {
        val params = if (commentId == null) "entry/$entryId" else "entry/$entryId/$commentId"
        networkUtils.sendGet("entries/vote", params, VoteResponse::class.java, successCallback, {})
    }

    fun unvoteEntry(entryId : Int, commentId : Int?, successCallback: (Any) -> Unit) {
        val params = if (commentId == null) "entry/$entryId" else "entry/$entryId/$commentId"
        networkUtils.sendGet("entries/unvote", params, VoteResponse::class.java, successCallback, {})
    }
}