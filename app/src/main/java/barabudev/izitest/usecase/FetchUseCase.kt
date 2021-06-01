package barabudev.izitest.usecase

import barabudev.izitest.repository.ExchangeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchUseCase @Inject constructor(private val repo: ExchangeRepo) {
    fun fetchData(user: String = "ciscoff"): Flow<String> =
        repo.fetchData(user).map { it.uppercase() }
}