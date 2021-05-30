package barabudev.izitest.presentation

import androidx.lifecycle.*
import barabudev.izitest.usecase.FetchUseCase
import barabudev.izitest.usecase.WriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModelImpl(
    private val fetchUseCase: FetchUseCase,
    private val writeUseCase: WriteUseCase
) : ViewModel(), MainViewModel {

    // TODO Проверить в каком потоке Activity будет получать
    val cache = liveData(Dispatchers.IO) {
        ('a'..'z').forEach { emit("'$it' in thread ${Thread.currentThread().name}") }
    }

    // TODO Проверить в каком потоке Activity будет получать
    override val repositories = fetchUseCase.fetchData().asLiveData(Dispatchers.IO)

    override fun sendData(data: List<String>) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                writeUseCase.writeData(data)
            }
        }
    }

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