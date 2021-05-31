package barabudev.izitest.presentation

import androidx.lifecycle.*
import barabudev.izitest.usecase.FetchUseCase
import barabudev.izitest.usecase.WriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModelImpl(
    private val fetchUseCase: FetchUseCase,
    private val writeUseCase: WriteUseCase
) : ViewModel(), MainViewModel {

    val liveData = liveData<String> {
        while (true) {
            delay(1000)
            emit("work")
        }
    }

    // TODO Проверить в каком потоке Activity будет получать
    /**
     * Вот тут написано, что emit - это suspend и она останавливается ожидая
     * когда её аргумент попадет в Main-поток. Получается, блок кода может
     * работать на любом диспетчере, но результат все равно попадет в Main.
     * https://developer.android.com/topic/libraries/architecture/coroutines
     * 'Each emit() call suspends the execution of the block until the LiveData
     * value is set on the main thread.'
     *
     * TODO - проверить !!!
     */
    val cache = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
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