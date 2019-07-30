package `fun`.gladkikh.app.price1c.util

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<TValue>(
    val preferences: SharedPreferences,
    private val name: String,
    private val defValue: TValue
) : ReadWriteProperty<Any?, TValue> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): TValue {
        with(preferences) {
            return when (defValue) {
                is Boolean -> (getBoolean(name, defValue) as? TValue) ?: defValue
                is Int -> (getInt(name, defValue) as TValue) ?: defValue
                is Float -> (getFloat(name, defValue) as TValue) ?: defValue
                is Long -> (getLong(name, defValue) as TValue) ?: defValue
                is String -> (getString(name, defValue) as TValue) ?: defValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: TValue) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }
    }

    class NotFoundRealizationException(defValue: Any?) : Exception("not found realization for $defValue")
}

/*
example

class UserStore(private val preferences: SharedPreferences) {

    var userName: String by PreferencesDelegate(preferences, USER_NAME, "")
    var userPhone: String by PreferencesDelegate(preferences, USER_PHONE, "")
    var isShowLicence: Boolean by PreferencesDelegate(preferences, USER_LICENCE, false)

    companion object {
        private const val USER_NAME = "user_name"
        private const val USER_PHONE = "user_phone"
        private const val USER_LICENCE = "user_licence"
    }
}
 */