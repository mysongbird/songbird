fun main(args: Array<String>) {
    Spotify.getCategories().forEach {
        println("-----------")
        Spotify.getPlaylistsWithCategory(it.id).forEach {
            println(it)
        }
    }
}