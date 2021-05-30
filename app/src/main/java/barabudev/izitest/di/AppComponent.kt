package barabudev.izitest.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface AppComponent {

    fun activityComponent(): ActivityComponent.Factory

    @Component.Builder
    interface Builder {
        fun with(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }
}