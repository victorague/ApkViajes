package net.victor.apkviajes.Activities.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_detalle_evento.*
import kotlinx.android.synthetic.main.activity_eventos_viajes.*
import net.victor.apkviajes.Activities.adapter.CustomAdapterEventos
import net.victor.apkviajes.Activities.model.Evento
import net.victor.apkviajes.R
import org.jetbrains.anko.indeterminateProgressDialog

class EventosViajesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterEventos
    private lateinit var docRef: DocumentSnapshot
    private lateinit var  query: QuerySnapshot
    private lateinit var eventosAL :ArrayList<Evento>
    private lateinit var evento:Evento
    private lateinit var idViaje : String
    private lateinit var uidUusario : String
    private lateinit var creador : String
    private lateinit var lugar : String
    private lateinit var fecha : String
    private lateinit var longitud : String
    private lateinit var latitud : String
    private lateinit var descripcion : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_viajes)
        var intent = getIntent().extras
        idViaje = intent.getString("idViaje").toString()
        uidUusario = intent.getString("uidUsuario")
        creador = intent.getString("creador")
        lugar = intent.getString("lugar")

        fecha = intent.getString("fecha")
        latitud = intent.getString("latitud")
        longitud = intent.getString("longitud")
        descripcion = intent.getString("descripcion")
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val mapFragment = fragmentManager
                .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)



        btnNuevoEvento.setOnClickListener{
            val intent = Intent(this , NuevoEventoActivity::class.java)
            intent.putExtra("idViaje", idViaje)
            startActivity(intent)
        }

        //Solo el usuario creador poría añadir neuvos eventos a su viaje
        if (uidUusario != mAuth.currentUser?.uid){
            btnNuevoEvento.hide()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showEventos(){

        eventosAL =  ArrayList()

        var dialog = indeterminateProgressDialog("Cargando eventos...")
        dialog.show()
        db.collection("eventos")
                .whereEqualTo("idViaje", idViaje).orderBy("fechaEvento")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            evento = Evento() //Firestore trabaja de forma asincrona, por lo que hay que declarar un nuevo viaje cada vez que empieza el nuevo bucle
                            //Log.d("pppppp", document.id + " => " + document.data)
                            evento.longitud = document.data.getValue("longitud").toString()
                            evento.latitud = document.data.getValue("latitud").toString()
                            evento.fechaEvento = document.data.getValue("fechaEvento").toString()
                            evento.idUsuario = document.data.getValue("idUsuario").toString()
                            evento.lugar = document.data.getValue("lugar").toString()
                            evento.descripcion = document.data.getValue("descripcion").toString()
                            evento.creador = document.data.getValue("creador").toString()
                            evento.idEvento = document.id


                            eventosAL.add(evento)



                        }


                    } else {
                        Log.d("Mal", "Error getting documents: ", task.exception)
                    }

                    rvEventos.layoutManager = LinearLayoutManager(this)
                    adapter = CustomAdapterEventos(this, R.layout.row_evento, eventosAL)
                    rvEventos.adapter = adapter
                    dialog.cancel()

                    if (eventosAL.isEmpty()){
                        tvHeader.text = descripcion
                        tvNoEventos.text = "Parece ser que aún no hay eventos para este viaje, si eres su creador, añade con el botón de la esquina inferior derecha"
                    } else{
                        tvNoEventos.text = ""
                        tvHeader.text = descripcion

                    }




                }

    }

    override fun onMapReady(map: GoogleMap?) {
        showEventos()

        var latitudDouble = latitud.toDouble()
        var longitudDouble = longitud.toDouble()
        var latlong: LatLng
        latlong = LatLng(latitudDouble,longitudDouble)


        map?.addMarker(MarkerOptions()
                .position(LatLng(latitudDouble,longitudDouble))
                .title("Lugar del viaje"))



        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong , 3F))




        //informacion sobre elementos del mapa https://developers.google.com/maps/documentation/android-api/views?hl=es-419

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        eventosAL.clear()
        showEventos()

    }


}

