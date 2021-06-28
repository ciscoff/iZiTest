package barabudev.izitest

import android.content.Context
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.jetbrains.annotations.NotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*

class GwtEduTest {

    private val message = "hello, dolly"


    @Test
    fun `any clause is given section`() {
        val mockkContext = mockk<Context>()

        every { mockkContext.getString(anyInt()) } returns message
        val result = mockkContext.getString(eq(5))

        assertEquals(message, result)
    }
}