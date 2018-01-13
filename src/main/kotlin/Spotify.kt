import com.google.gson.JsonObject
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.request.BaseRequest
import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequest
import java.util.*

object Spotify {


    init {
        UnirestSetup.verify()
    }

    data class CategoriesResponse(val categories: CategoriesData)
    data class CategoriesData(
        val href: String,
        val items: Array<CategoryData>,
        val limit: Int,
        val next: String?,
        val offset: Int,
        val previous: String?,
        val total: String)

    data class CategoryData(val href: String, val icons: Array<Icon>, val id: String, val name: String)
    data class Icon(val height: Int, val url: String, val width: String)

    fun getCategories() = getAuth<CategoriesResponse>("https://api.spotify.com/v1/browse/categories") {
        queryString("limit", "50")
    }.categories.items

    data class PlaylistsResponse(val playlists:PlaylistData)
    data class PlaylistData(val items: Array<Playlist>)
    data class Playlist(val id: String)
    fun getPlaylistsWithCategory(categoryId: String) =
        getAuth<PlaylistsResponse>("https://api.spotify.com/v1/browse/categories/$categoryId/playlists")
            .playlists.items

    inline fun <reified T> BaseRequest.safeGet(): T {
        val response = this.asObject(T::class.java)
        if (response.status != 200) {
            val text = response.rawBody.bufferedReader().lineSequence().map { "\t\t>$it" }.joinToString(System.lineSeparator())
            throw AssertionError("""Got back: ${response.status}
                |$text""".trimMargin())
        }
        return response
            .body
    }
    inline fun <reified T> postTokenless(url: String, body: String): T {
        val raw = Store.spotId + ":" + Store.spotSecret
        val data = Base64.getEncoder().encodeToString(raw.toByteArray())
        return Unirest.post(url)
            .header()
            .header("Authorization", "Basic $data")
            .body(body)
            .safeGet()
    }

    inline fun <reified T> getAuth(url: String, block: GetRequest.() -> Unit = {}): T {
        val get = Unirest.get(url)
        get.block()
        return get.header()
            .header("Authorization", "Bearer ${Store.spotToken}")
            .safeGet()
    }

    fun <T : HttpRequest> T.header(): T {
        this.header("cache-control", "no-cache")
            .header("content-type", "application/x-www-form-urlencoded")
        return this
    }

    fun makeToken(): String {
        val response = postTokenless<JsonObject>("https://accounts.spotify.com/api/token", "grant_type=client_credentials")
        return response.asJsonObject.get("access_token").asString
    }
}