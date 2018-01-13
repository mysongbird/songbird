import com.mashape.unirest.http.Unirest
import org.json.JSONObject
import java.util.*

fun main(args: Array<String>) {
    println(makeToken())
}

fun makeToken(): String {
    val raw = Store.spotId + ":" + Store.spotSecret
    val data = Base64.getEncoder().encodeToString(raw.toByteArray())
    val body = JSONObject()
    body.put("grant_type", "client_credentials")
    val r = Unirest.post("https://accounts.spotify.com/api/token")
        .header("Authorization", "Basic $data")
        .body(body)
        .asString()
        .body
    println(r)
    return ""
}