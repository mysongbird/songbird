package xyz.mysongbird.songbird.spotify.models

data class PlaylistTracks(
        val href: String,
        val total: Int
)

data class PlaylistSimple(
        val collaborative: Boolean,
        val external_urls: Map<String, String>,
        val href: String,
        val id: String,
        val images: Array<Image>,
        val name: String,
        val owner: UserPublic,
        val public: Boolean?,
        val snapshot_id: String,
        val tracks: PlaylistTracks,
        val type: String,
        val uri: String
)

data class PlaylistFull(
        val collaborative: Boolean,
        val external_urls: Map<String, String>,
        val href: String,
        val id: String,
        val images: Array<Image>,
        val name: String,
        val owner: UserPublic,
        val public: Boolean?,
        val snapshot_id: String,
//        val tracks: PlaylistTracks,
        val type: String,
        val uri: String
)