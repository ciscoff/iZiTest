package barabudev.izitest.di

import barabudev.izitest.data.CoolPrinter
import barabudev.izitest.data.CoolPrinterImpl
import barabudev.izitest.presentation.MainViewModel
import barabudev.izitest.usecase.FetchUseCase
import barabudev.izitest.usecase.WriteUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun coolPrinter() : CoolPrinter = CoolPrinterImpl()

    @Singleton
    @Provides
    fun viewModelFactory(
        fetchUseCase: FetchUseCase,
        writeUseCase: WriteUseCase
    ): MainViewModel.Factory = MainViewModel.Factory(fetchUseCase, writeUseCase)
}