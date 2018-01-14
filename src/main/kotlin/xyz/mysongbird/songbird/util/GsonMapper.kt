package xyz.mysongbird.songbird.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest

class GsonMapper : ObjectMapper {

    companion object {
        private val gson = Gson()
        private var ready = false

        fun init() {
            if (!ready) {
                ready = true
                Unirest.setObjectMapper(GsonMapper())
            }
        }

        init {
            init()
        }
    }

    override fun <T : Any> readValue(value: String?, type: Class<T>): T? {
        return try {
            gson.fromJson(value, type)
        } catch (e: JsonSyntaxException) {
            throw RuntimeException(e)
        }
    }

    override fun writeValue(value: Any?): String {
        return try {
            gson.toJson(value)
        } catch (e: JsonSyntaxException) {
            throw RuntimeException(e)
        }
    }
}