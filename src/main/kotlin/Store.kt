import kotlin.reflect.KProperty

object Store {

    class PropertyDelegate(val name: String) {
        operator fun getValue(t: Any, string: KProperty<*>): String? {
            return System.getProperty(name)
        }
    }

    val spotId by PropertyDelegate("SPOT_ID")
    val spotSecret by PropertyDelegate("SPOT_SECRET")
    val spotToken by PropertyDelegate("SPOT_TOKEN")
}