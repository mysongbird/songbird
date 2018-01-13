import kotlin.reflect.KProperty

object Store {

    class PropertyDelegate(private val name: String) {
        
        operator fun getValue(thisRef: Any, property: KProperty<*>): String {
            return System.getenv(name) ?: throw AssertionError("Unset property: $name")
        }
    }

    val SPOT_ID by PropertyDelegate("SPOT_ID")
    val SPOT_SECRET by PropertyDelegate("SPOT_SECRET")
    val SPOT_TOKEN by PropertyDelegate("SPOT_TOKEN")
}