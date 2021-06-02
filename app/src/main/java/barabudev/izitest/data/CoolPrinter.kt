package barabudev.izitest.data

import java.util.concurrent.TimeUnit

interface CoolPrinter {
    companion object {
        val DELAY = TimeUnit.MILLISECONDS.toMillis(1000)
    }

    suspend fun printData(data: List<String>)
}