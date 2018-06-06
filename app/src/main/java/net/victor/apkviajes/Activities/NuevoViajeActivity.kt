package net.victor.apkviajes.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_viaje.*
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class NuevoViajeActivity : AppCompatActivity() {

    val builder = PlacePicker.IntentBuilder()
    val PLACE_PICKER_REQUEST = 1
    var db = FirebaseFirestore.getInstance()

    private lateinit var mAuth: FirebaseAuth
    val viaje = Viaje()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_viaje)
        mAuth = FirebaseAuth.getInstance()

        // Create a new user with a first and last name
        viaje.lugar = inputLugarViaje.text.toString()
        viaje.descripcion = inputDescripcionViaje.text.toString()


        btnUbicacion.setOnClickListener{

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
            } catch (e: GooglePlayServicesRepairableException) {

            } catch (e: GooglePlayServicesNotAvailableException) {

            }

        }

        btnEmpezarViaje.setOnClickListener {
            toast("Rellena todos los camposy elige ubicacion")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val lugar = PlacePicker.getPlace(this, data)

               /* val latitud = place.latLng.latitude
                val longitud = place.latLng.longitude */
            }
        }


        val lugar = PlacePicker.getPlace(this, data)


        viaje.descripcion = inputDescripcionViaje.text.toString()
        viaje.lugar = inputLugarViaje.text.toString()
        viaje.latitud = lugar.latLng.latitude.toString()
        viaje.longitud = lugar.latLng.longitude.toString()
        viaje.idUsuario = mAuth.uid.toString()
        tvUbicacionElegida.text = lugar.name.toString()




        btnEmpezarViaje.setOnClickListener {
            if(viaje.descripcion != "" && viaje.lugar != "" &&  viaje.latitud != "" && viaje.longitud != "") /*SOlo latitud porque siempre coge al mismo timepo longitud y latitud)*/{
                // Add a new document with a generated ID
                db.collection("viajes")
                        .add(viaje)
                        .addOnSuccessListener { toast("Se ha creado el viaje correctamente") }
                        .addOnFailureListener { toast("Error al crear el viaje") }
                finish()
            }else{
                toast("¡Rellena todos los campos!")

            }

        }
    }




    }

