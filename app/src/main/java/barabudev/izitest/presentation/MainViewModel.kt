package barabudev.izitest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import barabudev.izitest.usecase.FetchUseCase
import barabudev.izitest.usecase.WriteUseCase

interface MainViewModel {
    val repositories: LiveData<String>
    fun sendData(data: List<String>)

    class Factory(
        private val fetchUseCase: FetchUseCase,
        private val writeUseCase: WriteUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass
                .getConstructor(FetchUseCase::class.java, WriteUseCase::class.java)
                .newInstance(fetchUseCase, writeUseCase)
        }
    }
}