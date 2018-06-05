package net.victor.apkviajes

import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_confirmacion.*
import org.jetbrains.anko.toast


/**
 * A login screen that offers login via email/password.
 */
class RegisterActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {
    override fun onLoaderReset(loader: Loader<Cursor>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //defining firebaseauth object
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance()


        fun register() {
            this.mAuth.createUserWithEmailAndPassword(emailRegister.text.toString(), passwordRegister.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                toast("Usuario Registrado Correctamente")
                val firebaseUser = mAuth.currentUser!!
                // Si se crea la cuenta correctamente, abrimos la activity de confirmacion que se loguee
                firebaseUser.sendEmailVerification()
                val intent = Intent(this , ActivityConfirmacion::class.java)
                startActivity(intent)
                finish()

            } else {
                toast("Error al registrar el nuevo usuario")
            }
        }

    }
        btnRegistro.setOnClickListener{
            register()
        }



        }
    }









