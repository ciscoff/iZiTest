package barabudev.izitest

import barabudev.izitest.ClassCast.Companion.CLASS_INT
import barabudev.izitest.ClassCast.Companion.CLASS_LONG
import barabudev.izitest.ClassCast.Companion.CLASS_STRING
import barabudev.izitest.ClassCast.Companion.CLASS_UNKNOWN
import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.reflect.KClass

class ClassCast<T : Any>(private val clazz: KClass<T>) {

    fun detectClassName(): String {

        return when (clazz) {
            String::class -> CLASS_STRING
            Long::class -> CLASS_LONG
            Int::class -> CLASS_INT
            else -> CLASS_UNKNOWN
        }
    }

    companion object {
        const val CLASS_STRING = "String"
        const val CLASS_LONG = "Long"
        const val CLASS_INT = "Int"
        const val CLASS_UNKNOWN = "NaN"
    }
}


class ClassCastTest {

//    @Suppress("UNCHECKED_CAST")
//    fun <T : Any> SharedPreferences.getOrNull(
//        key: String,
//        clazz: Class<T>
//    ): T? {
//        return if (!contains(key)) null
//        else when (clazz) {
//            String::class -> getString(key, EMPTY_STRING) as T
//            Int::class -> getInt(key, -1) as T
//            Float::class -> getFloat(key, -1f) as T
//            Long::class -> getLong(key, -1L) as T
//            Boolean::class -> getBoolean(key, false) as T
//            else -> throw TypeNotSupportedException()
//        }
//    }

    @Test
    fun `test String Class casting`() {
        val stringVal = "string"
        val stringClassCast = ClassCast(stringVal::class)
        assertEquals(CLASS_STRING, stringClassCast.detectClassName())
    }

    @Test
    fun `test Int Class casting`() {
        val intVal = 10
        val intClassCast = ClassCast(intVal::class)
        assertEquals(CLASS_INT, intClassCast.detectClassName())
    }

    @Test
    fun `test Long Class casting`() {
        val longVal = 10L
        val longClassCast = ClassCast(longVal::class)
        assertEquals(CLASS_LONG, longClassCast.detectClassName())
    }

    @Test
    fun `test Double Class casting`() {
        val doubleVal = 10.0
        val doubleClassCast = ClassCast(doubleVal::class)
        assertEquals(CLASS_UNKNOWN, doubleClassCast.detectClassName())
    }
}