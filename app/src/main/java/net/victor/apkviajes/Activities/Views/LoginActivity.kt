package net.victor.apkviajes

import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.Loader
import android.database.Cursor
import android.os.Bundle

import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()


        fun login(){
            if(tvEmailLogin.text.toString() != "" && tvPasswordLogin.text.toString() != ""){
                mAuth.signInWithEmailAndPassword(tvEmailLogin.text.toString(), tvPasswordLogin.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        toast("Usuario logueado correctamente")
                        val firebaseUser = mAuth.currentUser
                        val intent = Intent(this , MainActivity::class.java)
                        intent.putExtra("firebaseUser",  firebaseUser.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        toast(task.exception?.localizedMessage.toString())
                    }

                    // ...
                }
            }else{toast("Rellene todos los campos")}



        }



        btnForget.setOnClickListener{
            val intent = Intent(this , ActivityForget::class.java)
            startActivity(intent)

        }


        btnLogin.setOnClickListener{
            login()
        }
    }
}
