package barabudev.izitest.utils

import android.content.SharedPreferences

class TypeNotSupportedException(message: String = NOT_SUPPORTED_TYPE) :
    Exception(message) {

    companion object {
        const val NOT_SUPPORTED_TYPE = "Not supported type"
    }
}

fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> editor { putString(key, value) }
        is Int? -> editor { putInt(key, value as Int) }
        is Float? -> editor { putFloat(key, value as Float) }
        is Long? -> editor { putLong(key, value as Long) }
        is Boolean? -> editor { putBoolean(key, value as Boolean) }
        else -> throw TypeNotSupportedException()
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> SharedPreferences.getOrNull(
    key: String,
    clazz: Class<T>
): T? {
    return if (!contains(key)) null
    else when (clazz) {
        String::class -> getString(key, EMPTY_STRING) as T
        Int::class -> getInt(key, -1) as T
        Float::class -> getFloat(key, -1f) as T
        Long::class -> getLong(key, -1L) as T
        Boolean::class -> getBoolean(key, false) as T
        else -> throw TypeNotSupportedException()
    }
}

/**
 * NOTE: нет возможности mock'ать inline функции.
 * см. https://github.com/mockk/mockk/issues/27
 */
inline fun <reified T : Any> SharedPreferences.getOrNull(
    key: String
): T? {
    return if (!contains(key)) null
    else when (T::class) {
        String::class -> getString(key, EMPTY_STRING) as T
        Int::class -> getInt(key, -1) as T
        Float::class -> getFloat(key, -1f) as T
        Long::class -> getLong(key, -1L) as T
        Boolean::class -> getBoolean(key, false) as T
        else -> throw TypeNotSupportedException()
    }
}

fun SharedPreferences.editor(
    operation: SharedPreferences.Editor.() -> SharedPreferences.Editor
) {
    edit().operation().apply()
}