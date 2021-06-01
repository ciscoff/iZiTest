package barabudev.izitest.di

import barabudev.izitest.presentation.MainViewModel
import barabudev.izitest.presentation.MainViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    fun viewModel(factory: MainViewModel.Factory): MainViewModel {
        return factory.create(MainViewModelImpl::class.java)
    }
}