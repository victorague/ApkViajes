package net.victor.apkviajes.Activities.Views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import net.victor.apkviajes.R

import kotlinx.android.synthetic.main.activity_mis_viajes.*

class MisViajesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_viajes)
        setSupportActionBar(toolbar)













        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
