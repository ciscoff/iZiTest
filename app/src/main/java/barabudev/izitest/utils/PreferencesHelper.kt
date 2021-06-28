package barabudev.izitest.utils

import android.content.SharedPreferences
import com.squareup.moshi.JsonDataException
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Чтение/Запись в SharedPreferences. Примитивные типы обрабатываются как есть, любой
 * прочий объект упаковывается в JSON.
 */
class PreferencesHelper @Inject constructor(val preferences: SharedPreferences) {

    inline fun <reified T: Any> put(key: String, value: T) {
        try {
            preferences.set(key, value)
        } catch (e: TypeNotSupportedException) {
            put(key, value, T::class)
        }
    }

    @PublishedApi
    internal fun <T : Any> put(key: String, value: T, clazz: KClass<T>) {
        val jsonString = AdapterFactory.create(clazz.javaObjectType).toJson(value)
        preferences.set(key, jsonString)
    }

    inline fun <reified T : Any> get(key: String): T? {
        return try {
            preferences.getOrNull(key, T::class)
        } catch (e: TypeNotSupportedException) {
            get(key, T::class)
        }
    }

    fun <T : Any> get(key: String, clazz: KClass<T>): T? {

        return preferences.getOrNull<String>(key)?.let { `object` ->
            try {
                AdapterFactory.create(clazz.javaObjectType).fromJson(`object`)
            } catch (e: JsonDataException) {
                null
            }
        }
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}