import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest

class Spotify() {
    fun getPopular(s: String) {
        val v = get("https://api.spotify.com/v1/browse/featured-playlists")
        println(v.toString())
    }

    fun get(url: String): JsonNode {
        return Unirest.get(url)
            .asJson()
            .body
    }
}