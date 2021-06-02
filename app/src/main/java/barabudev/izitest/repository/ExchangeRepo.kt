package barabudev.izitest.repository

import barabudev.izitest.data.CoolPrinter
import barabudev.izitest.data.GitApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExchangeRepo @Inject constructor(
    private val git: GitApi,
    private val printer: CoolPrinter
) {
    suspend fun fetchData(user: String): Flow<String> {
        val response = git.repoList(user)

        return flow {
            if (response.isSuccessful && response.code() in (200..299)) {

                response.body()?.forEach {
                    delay(TimeUnit.SECONDS.toMillis(1))
                    emit(it.name)
                } ?: emit("empty data list")
            } else emit("error: ${response.code()}, ${response.errorBody()}")
        }
    }

    suspend fun sendData(data: List<String>) {
        printer.printData(data)
    }
}