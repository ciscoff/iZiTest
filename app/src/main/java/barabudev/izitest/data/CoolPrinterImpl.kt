package barabudev.izitest.data

import barabudev.izitest.data.CoolPrinter.Companion.DELAY
import barabudev.izitest.logIt
import kotlinx.coroutines.delay

/**
 * Класс эмулирует удаленный приемник данных
 */
class CoolPrinterImpl : CoolPrinter {

    override suspend fun printData(data: List<String>) {
        data.forEach {
            delay(DELAY)
            logIt(it)
        }
    }
}