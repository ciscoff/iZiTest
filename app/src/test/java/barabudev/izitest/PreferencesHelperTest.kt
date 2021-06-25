package barabudev.izitest

import android.content.SharedPreferences
import barabudev.izitest.utils.PreferencesHelper
import barabudev.izitest.utils.TypeNotSupportedException
import barabudev.izitest.utils.getOrNull
import barabudev.izitest.utils.set
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class PreferencesHelperTest {

    data class Foo(val name: String, val email: String, val age: Int)

    private lateinit var helper: PreferencesHelper
    private lateinit var mockPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    private val dataObject = Foo(name = "example", email = "t@t.com", age = 5)
    private val dataJson = "{\"name\":\"example\",\"email\":\"t@t.com\",\"age\":5}"
    private val dataJsonWrong = "{\"_name\":\"nan\",\"age\":\"t@t.com\"}"
    private val dataListInt = listOf(1, 2, 3)
    private val dataListIntJson = "[1,2,3]"
    private val dataListString = listOf("1", "2", "3")
    private val dataListStringJson = "[\"1\",\"2\",\"3\"]"
    private val dataString = "hello"
    private val dataDouble = 10.0
    private val dataLong = 100L

    @Before
    fun prepare() {
        mockEditor = mockk()
        mockPreferences = mockk(relaxUnitFun = true)
        helper = PreferencesHelper(mockPreferences)

        every { mockPreferences.edit() } returns mockEditor

        with(mockEditor) {
            every { putString(KEY, any()) } returns mockEditor
            every { putLong(KEY, any()) } returns mockEditor
            every { apply() } just Runs
        }

        mockkStatic("barabudev.izitest.utils.SharedPreferencesKt")
        every { any<SharedPreferences>().set(KEY, dataObject) } throws TypeNotSupportedException()
        every { any<SharedPreferences>().set(KEY, dataListInt) } throws TypeNotSupportedException()
        every {
            any<SharedPreferences>().set(
                KEY,
                dataListString
            )
        } throws TypeNotSupportedException()
    }

    @Test
    fun `wrong json throws an exception`() {

        with(mockPreferences) {
            every { contains(KEY_JSON) } returns true
            every { contains(KEY) } returns true
            every { getString(KEY_JSON, anyString()) } returns dataJsonWrong
            every { getOrNull(KEY_JSON, Foo::class.java) } throws TypeNotSupportedException()
            every { getOrNull(KEY_JSON, String::class.java) } returns dataJsonWrong

            helper = PreferencesHelper(this)

            val result = helper.get<Foo>(KEY_JSON)

            assertEquals(null, result)

            verify {
                mockPreferences.getOrNull<Foo>(KEY_JSON)
                mockPreferences.getOrNull<String>(KEY_JSON)
            }
        }
    }

    @Test
    fun `get Object should call getOrNull twice`() {

        with(mockPreferences) {
            every { contains(KEY_JSON) } returns true
            every { contains(KEY) } returns true
            every { getString(KEY_JSON, anyString()) } returns dataJson
            every { getOrNull(KEY_JSON, Foo::class.java) } throws TypeNotSupportedException()
            every { getOrNull(KEY_JSON, String::class.java) } returns dataJson

            helper = PreferencesHelper(this)

            val result = helper.get<Foo>(KEY_JSON)

            assertEquals(true, contains(KEY_JSON))
            assertEquals(true, contains(KEY))
            assertEquals(dataObject, result)

            verify {
                mockPreferences.getOrNull<Foo>(KEY_JSON)
                mockPreferences.getOrNull<String>(KEY_JSON)
            }
        }
    }

    @Test
    fun `put Object should call set(K, V) twice`() {

        var exceptionThrown = false

        try {
            helper.put(KEY, dataObject)

        } catch (e: TypeNotSupportedException) {
            exceptionThrown = true
        }

        assertTrue(!exceptionThrown)

        verify {
            mockPreferences.set(KEY, dataObject)
            mockPreferences.set(KEY, dataJson)
        }
    }

    @Test
    fun `put List_Int should call set(K, V) twice`() {
        var exceptionThrown = false

        try {
            helper.put(KEY, dataListInt)

        } catch (e: TypeNotSupportedException) {
            exceptionThrown = true
        }

        assertTrue(!exceptionThrown)

        verify {
            mockPreferences.set(KEY, dataListInt)
            mockPreferences.set(KEY, dataListIntJson)
        }
    }

    @Test
    fun `put List_String should call set(K, V) twice`() {
        var exceptionThrown = false

        try {
            helper.put(KEY, dataListString)

        } catch (e: TypeNotSupportedException) {
            exceptionThrown = true
        }

        assertTrue(!exceptionThrown)

        verify {
            mockPreferences.set(KEY, dataListString)
            mockPreferences.set(KEY, dataListStringJson)
        }
    }

    @Test
    fun `put String should call set(K, V) one time`() {

        helper.put(KEY, dataString)

        verify(exactly = 1) {
            mockPreferences.set(KEY, dataString)
        }
    }

    @Test
    fun `put Long should call set(K, V) one time`() {

        helper.put(KEY, dataLong)

        verify(exactly = 1) {
            mockPreferences.set(KEY, dataLong)
        }
    }

    @Test
    fun `put Double should call set(K, V) twice`() {

        // Double считается неизвестным типом
        helper.put(KEY, dataDouble)

        verify(exactly = 2) {
            mockPreferences.set(KEY, any())
        }
    }

    companion object {
        const val KEY = "key"
        const val KEY_JSON = "key_json"
    }
}