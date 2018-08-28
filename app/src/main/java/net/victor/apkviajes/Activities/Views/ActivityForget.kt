package net.victor.apkviajes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget.*
import org.jetbrains.anko.toast

class ActivityForget : AppCompatActivity() {


    lateinit var direccion:String
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        mAuth = FirebaseAuth.getInstance()



        btnRestablecimiento.setOnClickListener{
            if(inputEmailForget.text.toString() == null) {
                    toast(R.string.introduzca_correo)
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmailForget.text.toString()).matches()){
                toast(R.string.formato_no_valido)
            }

            else{
                direccion = inputEmailForget.text.toString()
                mAuth.sendPasswordResetEmail(direccion)
                toast(R.string.breve_recibir)
                finish()

            }
        }
    }
}
