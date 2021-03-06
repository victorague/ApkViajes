package net.victor.apkviajes.Activities.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_recientes.*
import kotlinx.android.synthetic.main.activity_resultado_busqueda.*
import net.victor.apkviajes.Activities.adapter.CustomAdapterViajes
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R
import org.jetbrains.anko.indeterminateProgressDialog


class ActivityRecientes : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterViajes
    private lateinit var docRef: DocumentSnapshot
    private lateinit var query: QuerySnapshot
    private lateinit var viajesAL: ArrayList<Viaje>
    private lateinit var viaje: Viaje
    private lateinit var lugarBuscado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recientes)



        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        showViajes()


        tvLimite.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                showViajes()
            }


        })}

    private fun showViajes() {
        viajesAL = ArrayList()
        var limite =
                if(tvLimite.text.toString() != ""){
                    tvLimite.text.toString().toLong()
                }else{
                    10
                }


        var dialog = indeterminateProgressDialog("Cargando Viajes...")
        dialog.show()
        db.collection("viajes")
                .orderBy("fechaCreacion", Query.Direction.DESCENDING).limit(limite)
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

                    rvViajesRecientes.layoutManager = LinearLayoutManager(this)
                    adapter = CustomAdapterViajes(this, R.layout.row_mis_viajes, viajesAL)
                    rvViajesRecientes.adapter = adapter
                    dialog.cancel()

                }
    }
}
