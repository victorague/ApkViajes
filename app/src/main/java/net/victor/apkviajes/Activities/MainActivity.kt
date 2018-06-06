package net.victor.apkviajes

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main.*
import net.victor.apkviajes.Activities.NuevoViajeActivity
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var usuarioLogeado:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()
        usuarioLogeado =  mAuth.currentUser?.email.toString()



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        btnNuevoViaje.setOnClickListener{
            val intent = Intent(this , NuevoViajeActivity::class.java)
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
            tvMailMainHeader.text = "Parece ser que aún no has iniciado sesión..."
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {

                val intent = Intent(this , LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_register -> {
                val intent = Intent(this , RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                alert("¿Quieres cerrar la sesion?", "Cerrar Sesion") {
                    positiveButton("Si") {
                        mAuth.signOut()
                        recreate()
                    }
                    negativeButton("No") { }
                }.show()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
