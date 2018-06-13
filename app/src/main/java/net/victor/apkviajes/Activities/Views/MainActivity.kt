package net.victor.apkviajes

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import android.content.Intent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main.*
import net.victor.apkviajes.Activities.Views.BuscarViajeActivity
import net.victor.apkviajes.Activities.Views.BuscarViajeUsuarioActivity
import net.victor.apkviajes.Activities.Views.MisViajesActivity
import net.victor.apkviajes.Activities.Views.NuevoViajeActivity
import net.victor.apkviajes.Activities.adapter.CustomAdapterViajes
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var usuarioLogeado:String
    private lateinit var viewMenu:NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()
        usuarioLogeado =  mAuth.currentUser?.email.toString()
        var isVerified = mAuth.currentUser?.isEmailVerified

        viewMenu = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu = viewMenu.getMenu()

        if(mAuth.currentUser != null){
            nav_Menu.findItem(R.id.nav_login).setVisible(false)
            nav_Menu.findItem(R.id.nav_register).setVisible(false)

        }else if(mAuth.currentUser == null){
            nav_Menu.findItem(R.id.nav_logout).setVisible(false)
        }



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        btnNuevoViaje.setOnClickListener{
            if(mAuth.currentUser != null && isVerified == true){
                val intent = Intent(this , NuevoViajeActivity::class.java)
                startActivity(intent)
            }else if(mAuth != null && isVerified == false){
                alert("Debes verificar tu direccion de correo", "Verificacion de correo") {
                    positiveButton("Ya lo he verificado") {
                        var intent = Intent(this.ctx , LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        mAuth.signOut()



                    }
                    negativeButton("Vuelve a enviarme el correo") {
                        mAuth.currentUser?.sendEmailVerification()
                        toast("Por favor, compruebe su bandeja de entrada")
                    }
                }.show()

            }else{
                toast("¡Debes estar registrado para poder crear tu propio viaje!")
            }
        }

        btnMisViajes.setOnClickListener {
            if (mAuth.currentUser != null && isVerified == true) {
                val intent = Intent(this, MisViajesActivity::class.java)
                startActivity(intent)
            }else if(mAuth != null && isVerified == false){
                toast("¡Debes confirmar tu cuenta antes de poder crear y ver tus viajes!. Comprueba la bandeja de entrada de tu correo electrónico.")
            } else {
                toast("¡Debes estar regitrado para poder crear y ver tus viajes!")
            }
        }

        btnBuscarViaje.setOnClickListener {
            val intent = Intent(this, BuscarViajeActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        if(mAuth.currentUser != null){
            tvMailMainHeader.text = usuarioLogeado
        }
        if(mAuth.currentUser == null){
            //tvMailMainHeader.text = "Parece ser que aún no has iniciado sesión... -> Deshabilitado porque creaba crashes en algunos dispositivos"
        }


        return true
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {

                if(mAuth.currentUser == null){
                    val intent = Intent(this , LoginActivity::class.java)
                    startActivity(intent)
                }
                else{toast("¡Ya está logueado!")}

            }


            R.id.nav_register -> {
                if(mAuth.currentUser == null){
                    val intent = Intent(this , RegisterActivity::class.java)
                    startActivity(intent)
                }else{toast("por favor, cierre sesión antes de crear una nueva cuenta")}

            }

            R.id.nav_logout -> {
                if(mAuth.currentUser != null){
                    alert("¿Quieres cerrar la sesion?", "Cerrar Sesion") {
                        positiveButton("Si") {
                            recreate()
                            mAuth.signOut()

                        }
                        negativeButton("No") { }
                    }.show()

                } else {toast("¡Debes estar logueado!")}

            }

            R.id.nav_amigos -> {
                val intent = Intent(this , BuscarViajeUsuarioActivity::class.java)
                startActivity(intent)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
