import com.mashape.unirest.http.Unirest
import java.util.*

fun main(args: Array<String>) {
    println(makeToken())
}

fun makeToken(): String {
    val raw = Store.spotId + ":" + Store.spotSecret
    val data = Base64.getEncoder().encodeToString(raw.toByteArray())
    val response = Unirest.post("https://accounts.spotify.com/api/token")
        .header("authorization", "Basic $data")
        .header("cache-control", "no-cache")
        .header("content-type", "application/x-www-form-urlencoded")
        .body("grant_type=client_credentials")
        .asJson()
        .body
    return response.`object`.getString("access_token")
}