package net.victor.apkviajes.Activities.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_resultado_busqueda.*
import net.victor.apkviajes.Activities.adapter.CustomAdapterViajes
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R
import org.jetbrains.anko.indeterminateProgressDialog

class ResultadoBusquedaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterViajes
    private lateinit var docRef: DocumentSnapshot
    private lateinit var  query: QuerySnapshot
    private lateinit var viajesAL:ArrayList<Viaje>
    private lateinit var viaje:Viaje
    private lateinit var lugarBuscado:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_busqueda)
        var intent = getIntent().extras
         lugarBuscado  = intent.getString("lugarBuscado")




        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        showViajes()


    }

    private fun showViajes(){

        viajesAL =  ArrayList()

        var dialog = indeterminateProgressDialog("Cargando Viajes...")
        dialog.show()
        db.collection("viajes")
                .whereEqualTo("lugar", lugarBuscado)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            viaje = Viaje() //Firestore trabaja de forma asincrona, por lo que hay que declarar un nuevo viaje cada vez que empieza el nuevo bucle

                            //Log.d("pppppp", document.id + " => " + document.data)
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
                        //Log.d("Mal", "Error getting documents: ", task.exception)
                    }

                    rvViajesBuscados.layoutManager = LinearLayoutManager(this)
                    adapter = CustomAdapterViajes(this, R.layout.row_mis_viajes, viajesAL)
                    rvViajesBuscados.adapter = adapter
                    dialog.cancel()

                    if (viajesAL.isEmpty()){
                        tvNoViajes.text = "Nadie ha hecho aún viajes a "+ lugarBuscado + ", ¿por qué no empiezas? ;)"

                    }



                }

    }




}



