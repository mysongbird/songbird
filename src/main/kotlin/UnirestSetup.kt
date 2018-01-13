import com.google.gson.Gson
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest

object UnirestSetup {
    fun verify() { /* Guarantees init has ran. */}
    init {
        Unirest.setObjectMapper(object : ObjectMapper {
            private val gson = Gson()

            override fun <T> readValue(s: String, aClass: Class<T>): T {
                try {
                    return gson.fromJson(s, aClass)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }

            }

            override fun writeValue(o: Any): String {
                try {
                    return gson.toJson(o)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }

            }
        })
    }
}