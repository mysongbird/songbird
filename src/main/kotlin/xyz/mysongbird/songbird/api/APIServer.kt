package xyz.mysongbird.songbird.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.io.Closeable
import java.io.PrintStream
import java.net.InetSocketAddress
import kotlin.math.roundToInt

data class Response(val data: Any)
data class SearchResult(val icon: String, val name: String, val id: String)
class APIServer(val port: Int): Closeable {
    val GSON = GsonBuilder().setPrettyPrinting().create()
    val server: HttpServer = HttpServer.create(InetSocketAddress(port), -1)

    fun error(code: Int, message: String): Pair<Int, String> {
        val body = JsonObject()
        val error = JsonObject()
        body.add("error", error)
        error.addProperty("code", code)
        error.addProperty("message", message)
        return code to body.toString()
    }

    fun success(message: Any): Pair<Int, String> {
        return 200 to GSON.toJson(Response(message))
    }

    fun handleRequest(exchange: HttpExchange) {
        val path = exchange.requestURI.path.split("/").drop(1)
        val `404` = error(404, "Not found")
        val response = try {
            when (path[0]) {
                "song" -> {
                    val songID = path[1]
                    when (path[2]) {
                        "popularity" -> {
                            val message = popSearch(songID)
                            val success = success(message)
                            success
                        }
                        else -> `404`
                    }
                }
                "search" -> {
                    val query = exchange.requestURI.query
                    val parts = query.split("&").associate {
                        val parts = it.split("=")
                        parts[0] to parts[1]
                    }
                    if ("q" in parts) {
                        System.err.println(parts)
                        success(listOf(
                                SearchResult("https://www.jrtapsell.co.uk/img/icon64.jpg", "foo", "foo"),
                                SearchResult("https://www.jrtapsell.co.uk/img/icon64.jpg", "bar", "bar"),
                                SearchResult("https://www.jrtapsell.co.uk/img/icon64.jpg", "bazz", "bazz")))
                    } else `404`
                }
                else -> `404`
            }
        } catch (ex: Throwable) {
            error(500, ex.localizedMessage)
        }
        exchange.sendResponseHeaders(response.first, response.second.length.toLong())
        PrintStream(exchange.responseBody).use {
            it.print(response.second)
            it.flush()
        }
    }

    private fun popSearch(songID: String): Any {
        return (0..36)
            .map { "${(it % 12) + 1}/${15 + (it / 12)}"  }
            .associate { it to (Math.random() * 10.0).roundToInt() }
    }

    init {
        server.createContext("/", this::handleRequest)
        server.start()
    }
    override fun close() {
        server.stop(0)
    }

}

fun main(args: Array<String>) {
    APIServer(8000).use {
        while(readLine() != "exit"){}
    }
}