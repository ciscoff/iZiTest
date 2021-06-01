package barabudev.izitest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import barabudev.izitest.IziTestApp
import barabudev.izitest.R
import barabudev.izitest.logIt
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as IziTestApp)
            .appComponent
            .activityComponent()
            .create()
            .inject(this)

        setContentView(R.layout.activity_main)

        viewModel.repositories.observe(this){
            logIt(it)
        }
    }
}