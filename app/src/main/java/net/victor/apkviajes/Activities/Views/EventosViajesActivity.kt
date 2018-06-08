package net.victor.apkviajes.Activities.Views

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_eventos_viajes.*
import net.victor.apkviajes.R
import net.victor.apkviajes.RegisterActivity

class EventosViajesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_viajes)
        var intent = getIntent().extras
        var idViaje = intent.getString("idViaje").toString()

        btnAddEvento.setOnClickListener {
            val intent = Intent(this , NuevoEventoActivity::class.java)
            intent.putExtra("idViaje",idViaje)
            startActivity(intent)
        }
    }
}
