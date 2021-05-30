package barabudev.izitest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import barabudev.izitest.IziTestApp
import barabudev.izitest.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as IziTestApp)
            .appComponent
            .activityComponent()
            .create()
            .inject(this)

        setContentView(R.layout.activity_main)
    }
}