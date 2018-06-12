package net.victor.apkviajes.Activities.Views

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_evento.*
import net.victor.apkviajes.Activities.model.Evento
import net.victor.apkviajes.R
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import android.text.Editable
import android.text.TextWatcher
import android.app.DatePickerDialog





class NuevoEventoActivity : AppCompatActivity() {


    val builder = PlacePicker.IntentBuilder()
    val PLACE_PICKER_REQUEST = 1
    var db = FirebaseFirestore.getInstance()

    private lateinit var mAuth: FirebaseAuth
    val evento = Evento()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nuevo_evento)
        mAuth = FirebaseAuth.getInstance()

        var intent = getIntent().extras
        var idViaje = intent.getString("idViaje").toString()
        evento.idViaje = idViaje
        evento.idUsuario = mAuth.currentUser!!.uid
        evento.creador = mAuth.currentUser?.email.toString()


        // Create a new user with a first and last name
        // evento.descripcion = tvDescripcionEvento.text.toString()




            btnFechaActualEvento.setOnClickListener {
                val sdf = SimpleDateFormat("dd-M-yyyy")
                val currentDateandTime = sdf.format(Date())
                evento.fechaEvento = currentDateandTime
                toast("Elegida fecha actual: "+evento.fechaEvento)
            }


            btnUbicacionEvento.setOnClickListener {
                try {
                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
                } catch (e: GooglePlayServicesRepairableException) {

                } catch (e: GooglePlayServicesNotAvailableException) {

                }

            }

            btnCrearEvento.setOnClickListener {
                toast("Rellena todos los campos y elige ubicacion")
            }

            btnElegirFechaEvento.setOnClickListener {
                val calendar = Calendar.getInstance()
                val dateFormatter = SimpleDateFormat("dddd-MM-yyyy", Locale.US)
                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    evento.fechaEvento = dayOfMonth.toString() + "-" + (monthOfYear+1).toString() + "-" + year.toString()
                    print(dateFormatter.format(newDate.time))
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()

            }




        /////////////////////////////COMPROBAR SI TEL TEXTO ESTA VACIO PARA HABILITAR BOTON EVENTO////////////////////////////////////
        btnUbicacionEvento.isEnabled = false

        tvDescripcionEvento.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub

                if (s.toString() == "") {
                    btnUbicacionEvento.isEnabled = false
                } else {
                    btnUbicacionEvento.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub

            }
        })
/////////////////////////////COMPROBAR SI TEL TEXTO ESTA VACIO PARA HABILITAR BOTON EVENTO////////////////////////////////////

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


            evento.descripcion = tvDescripcionEvento.text.toString()
            evento.lugar = lugar.name.toString()
            evento.latitud = lugar.latLng.latitude.toString()
            evento.longitud = lugar.latLng.longitude.toString()
            evento.idUsuario = mAuth.currentUser?.uid.toString()



            btnCrearEvento.setOnClickListener {
                Log.d("descripcion", evento.descripcion)
                Log.d("lugar", evento.lugar)
                Log.d("Latitud", evento.latitud)
                Log.d("Longitud", evento.longitud)
                Log.d("fecha", evento.fechaEvento)
                if (evento.descripcion != "" && evento.lugar != "" && evento.latitud != "" && evento.longitud != "" && evento.fechaEvento != "") /*SOlo latitud porque siempre coge al mismo timepo longitud y latitud)*/ {
                    // Add a new document with a generated ID
                    db.collection("eventos")
                            .add(evento)
                            .addOnSuccessListener { toast("Evento creado correctamente") }
                            .addOnFailureListener { toast("Error al crear el viaje") }
                    finish()

                } else {
                    toast("¡Rellena todos los campos!")

                }

            }
        }



    }

