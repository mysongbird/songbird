package xyz.mysongbird.songbird.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.io.Closeable
import java.net.InetSocketAddress

class Server(val port: Int) : Closeable {
    val server: HttpServer = HttpServer.create(InetSocketAddress(port), -1)

    companion object {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()

        object Status {
            val `404` = error(404, "Not found")
        }

        fun error(code: Int, message: String): Pair<Int, String> {
            val body = JsonObject()
            val error = JsonObject()

            body.add("error", error)
            error.addProperty("code", code)
            error.addProperty("message", message)

            return code to body.toString()
        }

        fun success(message: Any): Pair<Int, String> {
            return 200 to gson.toJson(Response(message))
        }
    }

    fun handleRequest(exchange: HttpExchange) {
        val path = exchange.requestURI.path.split("/").drop(1)
        val response = try {
            when (path[0]) {
                "song" -> {
                    val songId = path[1]
                    when (path[2]) {
                        "popularity" -> {
                            val message = object {}
                            success(message)
                        }
                        else -> Status.`404`
                    }
                }
                "search" -> {
                    val query = exchange.requestURI.query
                    val pairs = query.split("&").associate {
                        val (key, value) = it.split("=".toRegex(), 1)
                        key to value
                    }

                    if ("q" in pairs) {
                        success(listOf())
                    } else Status.`404`
                }
            }
        }
    }
}