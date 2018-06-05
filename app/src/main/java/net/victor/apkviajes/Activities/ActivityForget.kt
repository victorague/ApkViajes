package net.victor.apkviajes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
                toast("Intruduzca una dirección de correo electrónico")
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmailForget.text.toString()).matches()){
                toast("¡El formato del correco electrónico no es válido!")
            }

            else{
                direccion = inputEmailForget.text.toString()
                mAuth.sendPasswordResetEmail(direccion)
                toast("En breves recibirá el correo para restablecer su contraseña")
                finish()

            }
        }
    }
}
