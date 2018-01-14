package xyz.mysongbird.songbird.spotify.models

data class UserPublic(
        val display_name: String?,
        val external_urls: Map<String, String>,
        val followers: Followers,
        val href: String,
        val id: String,
        val images: Array<Image>,
        val type: String,
        val uri: String
)