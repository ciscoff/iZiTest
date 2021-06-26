package barabudev.izitest.utils

import android.content.SharedPreferences
import com.squareup.moshi.JsonDataException
import java.io.IOException
import javax.inject.Inject

/**
 * Чтение/Запись в SharedPreferences. Примитивные типы обрабатываются как есть, любой
 * прочий объект упаковывается в JSON.
 */
class PreferencesHelper @Inject constructor(val preferences: SharedPreferences) {

    inline fun <reified T> put(key: String, value: T) {
        try {
            preferences.set(key, value)
        } catch (e: TypeNotSupportedException) {
            put(key, value, T::class.java)
        }
    }

    @PublishedApi
    internal fun <T> put(key: String, value: T, clazz: Class<T>) {
        val jsonString = AdapterFactory.create(clazz).toJson(value)
        preferences.set(key, jsonString)
    }

    inline fun <reified T : Any> get(key: String): T? {
        return try {
            preferences.getOrNull(key, T::class.java)
        } catch (e: TypeNotSupportedException) {
            get(key, T::class.java)
        }
    }

    fun <T> get(key: String, clazz: Class<T>): T? {

        return preferences.getOrNull<String>(key)?.let { `object` ->
            try {
                AdapterFactory.create(clazz).fromJson(`object`)
            } catch (e: JsonDataException) {
                null
            }
        }
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}