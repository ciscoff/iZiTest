package barabudev.izitest.presentation

import androidx.lifecycle.LiveData

interface MainViewModel {
    val repositories: LiveData<String>
    fun sendData(data: List<String>)
}