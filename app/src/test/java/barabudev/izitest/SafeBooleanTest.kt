package barabudev.izitest

import org.junit.Assert.assertEquals
import org.junit.Test

val Boolean?.safeBoolean: Boolean
    get() {
        return this == true
    }

class SafeBooleanTest {

    private val nullBoolean: Boolean? = null
    private val falseBoolean: Boolean = false
    private val trueBoolean: Boolean = true

    @Test
    fun check_null_boolean_is_false() {
        assertEquals(nullBoolean.safeBoolean, false)
    }

    @Test
    fun check_null_boolean_inversion_is_true() {
        println("wow: ${nullBoolean.safeBoolean.not()}")
        assertEquals(nullBoolean.safeBoolean.not(), true)
    }

    @Test
    fun check_false_boolean_is_false() {
        assertEquals(falseBoolean.safeBoolean, false)
    }

    @Test
    fun check_true_boolean_is_true() {
        assertEquals(trueBoolean.safeBoolean, true)
    }
}