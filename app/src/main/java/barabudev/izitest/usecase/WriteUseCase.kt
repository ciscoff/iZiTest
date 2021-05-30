package barabudev.izitest.usecase

import barabudev.izitest.repository.ExchangeRepo
import javax.inject.Inject

class WriteUseCase @Inject constructor(private val repo: ExchangeRepo) {

    suspend fun writeData(data: List<String>) {
        repo.sendData(data.map { "${it}_${it.uppercase()}" })
    }

}