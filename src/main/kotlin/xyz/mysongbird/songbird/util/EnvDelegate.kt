package xyz.mysongbird.songbird.util

import kotlin.reflect.KProperty

class EnvDelegate(private val name: String) {

    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        return System.getenv(name) ?: throw AssertionError("Unset property: $name")
    }
}

fun env(name: String) = EnvDelegate(name)