package net.victor.apkviajes.Activities.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_buscar_viajes.*
import net.victor.apkviajes.R

class BuscarViajeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_viajes)

        btnBuscar.setOnClickListener {
            val intent = Intent(this, ResultadoBusquedaActivity::class.java)
            intent.putExtra("lugarBuscado", inputLugarBuscado.text.toString())
            startActivity(intent)
        }
    }
}
