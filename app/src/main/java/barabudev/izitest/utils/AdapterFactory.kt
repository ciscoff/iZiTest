package barabudev.izitest.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object AdapterFactory {

    @PublishedApi
    internal val moshi = Moshi.Builder().build()

    fun <T> create(clazz: Class<T>) : JsonAdapter<T> =
        moshi.adapter(clazz)

    inline fun <reified T> create(): JsonAdapter<T> =
        moshi.adapter(T::class.java)
}