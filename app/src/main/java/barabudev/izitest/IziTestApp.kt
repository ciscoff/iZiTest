package barabudev.izitest

import android.app.Application
import barabudev.izitest.di.AppComponent
import barabudev.izitest.di.DaggerAppComponent

class IziTestApp : Application() {
    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .with(this)
            .build()
    }
}