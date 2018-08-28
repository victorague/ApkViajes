package net.victor.apkviajes.Activities.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_buscar_viaje_usuario.*
import net.victor.apkviajes.R
import org.jetbrains.anko.toast

class BuscarViajeUsuarioActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_viaje_usuario)
        var usuarioBuscado : String = ""

        mAuth = FirebaseAuth.getInstance()



        btnBuscarUusario.setOnClickListener {
            usuarioBuscado = etUsuarioBuscar.text.toString()

            if(usuarioBuscado != "" && android.util.Patterns.EMAIL_ADDRESS.matcher(usuarioBuscado).matches()){
                val intent = Intent(this , ViajesUsuarioActivity::class.java)
                intent.putExtra("usuario", usuarioBuscado)
                startActivity(intent)
            }else if( !android.util.Patterns.EMAIL_ADDRESS.matcher(usuarioBuscado).matches()) {
                toast(R.string.formato_no_valido)

            }
        }







    }



}
