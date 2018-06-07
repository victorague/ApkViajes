package net.victor.apkviajes.Activities.Views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_mis_viajes.*
import kotlinx.android.synthetic.main.content_mis_viajes.*
import net.victor.apkviajes.Activities.adapter.CustomAdapterViajes
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R
import org.jetbrains.anko.toast


class MisViajesActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterViajes
    private lateinit var docRef: DocumentSnapshot
    private lateinit var  query:QuerySnapshot
    private lateinit var viajesAL:ArrayList<Viaje>
    private lateinit var viaje:Viaje



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_viajes)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        showViajes()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

     private fun showViajes(){

         viajesAL =  ArrayList()

         db.collection("viajes")
                 .whereEqualTo("idUsuario", mAuth.currentUser!!.uid)
                 .get()
                 .addOnCompleteListener { task ->
                     if (task.isSuccessful) {
                         for (i in task.result) {
                             viaje = Viaje()
                             Log.d("pppppp", i.id + " => " + i.data)
                             viaje.longitud = i.data.getValue("longitud").toString()
                             viaje.latitud = i.data.getValue("latitud").toString()
                             viaje.fechaInicio = i.data.getValue("fechaInicio").toString()
                             viaje.idUsuario = i.data.getValue("idUsuario").toString()
                             viaje.lugar = i.data.getValue("lugar").toString()
                             viaje.descripcion = i.data.getValue("descripcion").toString()


                             viajesAL.add(viaje)



                         }





                     } else {
                         Log.d("Mal", "Error getting documents: ", task.exception)
                     }


                     adapter = CustomAdapterViajes(this, R.layout.row_mis_viajes, viajesAL)
                     rvMisViajes.layoutManager = LinearLayoutManager(this)
                     rvMisViajes.adapter = adapter




                 }



     }


}
