package xyz.mysongbird.songbird.spotify

object Endpoints {
    const val API = "https://models.spotify.com/v1"

    object Browse {
        const val BROWSE = "$API/browse"

        object Categories {
            const val CATEGORIES = "$BROWSE/categories"
            const val CATEGORY = "$CATEGORIES/{0}"
            const val PLAYLISTS = "$CATEGORIES/{0}/playlist"
        }
    }

    const val TOKEN = "https://accounts.spotify.com/models/token"
}