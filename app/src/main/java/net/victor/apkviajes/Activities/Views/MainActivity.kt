package net.victor.apkviajes

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import net.victor.apkviajes.Activities.Views.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


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
                alert(getString(R.string.debes_verificar_correo), getString(R.string.titulo_verificacion_correo)) {
                    positiveButton(R.string.ya_he_verificado) {
                        var intent = Intent(this.ctx , LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        mAuth.signOut()



                    }
                    negativeButton(R.string.vuelve_enviar_correo) {
                        mAuth.currentUser?.sendEmailVerification()
                        toast(R.string.compruebe_bandeja)
                    }
                }.show()

            }else{
                toast(R.string.debes_registrar_viaje)
            }
        }

        btnMisViajes.setOnClickListener {
            if (mAuth.currentUser != null && isVerified == true) {
                val intent = Intent(this, MisViajesActivity::class.java)
                startActivity(intent)
            }else if(mAuth != null && isVerified == false){
                toast(R.string.verificar_comprueba_bandeja)
            } else {
                toast(R.string.debes_reegistrar_verycrer)
            }
        }

        btnBuscarViaje.setOnClickListener {
            val intent = Intent(this, BuscarViajeActivity::class.java)
            startActivity(intent)
        }

        btnViajesRecientes.setOnClickListener {
            val intent = Intent(this, ActivityRecientes::class.java)
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
                else{toast(R.string.ya_logeaado)}

            }


            R.id.nav_register -> {
                if(mAuth.currentUser == null){
                    val intent = Intent(this , RegisterActivity::class.java)
                    startActivity(intent)
                }else{toast(R.string.porfavor_cierre_sesion)}

            }

            R.id.nav_logout -> {
                if(mAuth.currentUser != null){
                    alert(getString(R.string.quieres_cerrar), getString(R.string.cerrar_sesion)) {
                        positiveButton(R.string.si) {
                            recreate()
                            mAuth.signOut()

                        }
                        negativeButton(R.string.no) { }
                    }.show()

                } else {toast(R.string.debes_estar_logeado)}

            }

            R.id.nav_amigos -> {
                val intent = Intent(this , BuscarViajeUsuarioActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_info -> {
                val intent = Intent(this , InfoActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
