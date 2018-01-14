package xyz.mysongbird.songbird.spotify

import com.google.gson.JsonObject
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.request.HttpRequest
import xyz.mysongbird.songbird.spotify.models.Category
import xyz.mysongbird.songbird.spotify.models.Paging
import xyz.mysongbird.songbird.spotify.models.PlaylistSimple
import xyz.mysongbird.songbird.util.GsonMapper
import xyz.mysongbird.songbird.util.Store
import xyz.mysongbird.songbird.util.validate
import java.text.MessageFormat
import kotlin.reflect.KClass

data class CategoryPlaylistsData(val playlists: Paging<PlaylistSimple>)
data class CategoriesData(val categories: Paging<Category>)

object Spotify {
    init {
        GsonMapper.init()
    }

    private fun <T : Any> get(url: String, kClass: KClass<T>, block: HttpRequest.() -> Unit = {}): HttpResponse<T> {
        val req = Unirest.get(url).addHeaders().apply(block)
        return req.asObject(kClass.java)
    }

    private inline fun <reified T : Any> get(url: String, noinline block: HttpRequest.() -> Unit = {}) = get(url, T::class, block)

    fun getCategory(id: String) = get<Category>(Endpoints.Browse.Categories.CATEGORY.fmt(id))

    fun getCategoryPlaylists(id: String) = get<CategoryPlaylistsData>(Endpoints.Browse.Categories.PLAYLISTS.fmt(id))

    fun getCategories() = get<CategoriesData>(Endpoints.Browse.Categories.CATEGORIES) {
        queryString("items", "50")
    }

    fun createToken(): String {
        val response = Unirest.post(Endpoints.TOKEN).apply {
            basicAuth(Store.SPOT_ID, Store.SPOT_SECRET)
            body("grant_type=client_credentials")
        }.asObject(JsonObject::class.java)

        validate(response.status == 200) {
            val text = response.rawBody
                    .bufferedReader()
                    .lineSequence()
                    .map { "\t\t>$it" }
                    .joinToString(System.lineSeparator())
            """Received ${response.status}
                |$text
            """.trimMargin()
        }

        return response.body["access_token"].asString
    }
}

private fun <T : HttpRequest> T.addHeaders(): T {
    header("cache-control", "no-cache")
    header("content-type", "application/x-www-form-urlencoded")
    return this
}

private fun <T : HttpRequest> T.auth(): T {
    header("Authorization", "Bearer ${Store.SPOT_TOKEN}")
    return this
}

private fun String.fmt(vararg args: String)
        = MessageFormat.format(this, args)