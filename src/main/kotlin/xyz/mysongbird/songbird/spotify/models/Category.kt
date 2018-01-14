package xyz.mysongbird.songbird.spotify.models

import xyz.mysongbird.songbird.spotify.Spotify

data class Category(
        val href: String,
        val icons: Array<Image>,
        val id: String,
        val name: String
)

val Category.playlists get() = Spotify.getCategoryPlaylists(id)