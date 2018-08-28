package net.victor.apkviajes.Activities.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_mis_viajes.*
import kotlinx.android.synthetic.main.content_mis_viajes.*
import net.victor.apkviajes.Activities.adapter.CustomAdapterViajes
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R

class ViajesUsuarioActivity : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterViajes
    private lateinit var docRef: DocumentSnapshot
    private lateinit var  query: QuerySnapshot
    private lateinit var viajesAL:ArrayList<Viaje>
    private lateinit var viaje: Viaje
    private  lateinit var idViaje:String
    private lateinit var  uidUusario:String
    private  lateinit var usuarioBuscado:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_viajes)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        usuarioBuscado = intent.getStringExtra("usuario")
        showViajes()




    }

    @SuppressLint("SetTextI18n")
    private fun showViajes(){

        viajesAL =  ArrayList()

        db.collection("viajes")
                .whereEqualTo("creador", usuarioBuscado)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            viaje = Viaje() //Firestore trabaja de forma asincrona, por lo que hay que declarar un nuevo viaje cada vez que empieza el nuevo bucle
                            Log.d("pppppp", document.id + " => " + document.data)
                            viaje.longitud = document.data.getValue("longitud").toString()
                            viaje.latitud = document.data.getValue("latitud").toString()
                            viaje.fechaInicio = document.data.getValue("fechaInicio").toString()
                            viaje.idUsuario = document.data.getValue("idUsuario").toString()
                            viaje.lugar = document.data.getValue("lugar").toString()
                            viaje.descripcion = document.data.getValue("descripcion").toString()
                            viaje.idViaje = document.id
                            viaje.creador = document.data.getValue("creador").toString()

                            viajesAL.add(viaje)



                        }


                    } else {
                        Log.d("Mal", "Error getting documents: ", task.exception)
                    }

                    rvMisViajes.layoutManager = LinearLayoutManager(this)
                    adapter = CustomAdapterViajes(this, R.layout.row_mis_viajes, viajesAL)
                    rvMisViajes.adapter = adapter


                    if (viajesAL.isEmpty()){
                        tvSinEventos.text = R.string.el_usuario.toString() +usuarioBuscado + R.string.no_existe_o_no_viajes
                    }else{
                        tvSinEventos.text = ""
                    }

                }

    }


    override fun onRestart() {
        super.onRestart()
        viajesAL.clear()
        showViajes()

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
}

