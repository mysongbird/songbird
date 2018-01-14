package xyz.mysongbird.songbird.spotify.models

data class Paging<T : Any>(
        val href: String,
        val items: Array<T>,
        val limit: Int,
        val next: String,
        val offset: Int,
        val previous: String,
        val total: Int
)