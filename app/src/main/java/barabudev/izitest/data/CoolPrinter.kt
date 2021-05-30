package barabudev.izitest.data

interface CoolPrinter {
    companion object {
        const val DELAY = 1000L
    }

    suspend fun printData(data: List<String>)
}