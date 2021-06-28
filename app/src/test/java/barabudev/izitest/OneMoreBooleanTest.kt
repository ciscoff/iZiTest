package barabudev.izitest

import org.junit.Assert.*
import org.junit.Test

class Manager(private val param: Boolean? = null) {
    fun isEnabled() : Boolean? = param
}

class OneMoreBooleanTest {

    private val managerNull : Manager? = null
    private val managerTrue = Manager(true)
    private val managerFalse = Manager(false)

    @Test
    fun null_manager_is_false() {
        assertEquals(managerNull?.isEnabled() == false, false)
    }

}