package net.victor.apkviajes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_confirmacion.*

class ActivityConfirmacion : AppCompatActivity() {

    override fun onBackPressed() {
    // Deshabilitamos el boton de atrás, para que el usuario tenga que pulsar el boton de aceptar si quiere continuar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion)


        btnAceptar.setOnClickListener{
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}

