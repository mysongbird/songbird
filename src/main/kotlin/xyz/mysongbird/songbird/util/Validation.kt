package xyz.mysongbird.songbird.util

inline fun validate(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        val message = lazyMessage()
        throw AssertionError(message)
    }
}