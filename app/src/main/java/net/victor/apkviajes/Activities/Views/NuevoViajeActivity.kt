package net.victor.apkviajes.Activities.Views

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_viaje.*
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.R
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


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

        viaje.descripcion = tvDescripcionViajeNuevoViaje.text.toString()
        viaje.creador = mAuth.currentUser?.email.toString()




        btnUbicacion.setOnClickListener{

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
            } catch (e: GooglePlayServicesRepairableException) {

            } catch (e: GooglePlayServicesNotAvailableException) {

            }

        }

        btnEmpezarViaje.setOnClickListener {
            toast(R.string.rellene_campos_ubicacion)
        }

        btnElegirFechaViaje.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dateFormatter = SimpleDateFormat("dddd-MM-yyyy", Locale.US)
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                viaje.fechaInicio = year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth.toString() //month +1 porque datepicker coge el mes con un numero menos
                print(dateFormatter.format(newDate.time))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()



        }



        btnFechaActualViaje.setOnClickListener{
            val sdf = SimpleDateFormat("yyyy-M-dd")
            val currentDateandTime = sdf.format(Date())
            viaje.fechaInicio = currentDateandTime.toString()
            toast("Elegida fecha actual")
        }

        /////////////////////////////COMPROBAR SI TEL TEXTO ESTA VACIO PARA HABILITAR BOTON EVENTO////////////////////////////////////
        btnUbicacion.isEnabled = false

        tvDescripcionViajeNuevoViaje.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                // TODO Auto-generated method stub

                if (s.toString() == "") {
                    btnUbicacion.isEnabled = false
                } else {
                    btnUbicacion.isEnabled = true
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


        viaje.descripcion = tvDescripcionViajeNuevoViaje.text.toString()
        viaje.lugar = lugar.name.toString()
        viaje.latitud = lugar.latLng.latitude.toString()
        viaje.longitud = lugar.latLng.longitude.toString()
        viaje.idUsuario = mAuth.uid.toString()
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDateandTime = sdf.format(Date())
        viaje.fechaCreacion = currentDateandTime.toString()




        btnEmpezarViaje.setOnClickListener {
            if(viaje.descripcion != "" && viaje.lugar != "" &&  viaje.latitud != "" && viaje.longitud != "" && viaje.fechaInicio != "") /*SOlo latitud porque siempre coge al mismo timepo longitud y latitud)*/{
                // Add a new document with a generated ID
                db.collection("viajes")
                        .add(viaje)
                        .addOnSuccessListener { toast(R.string.viaje_creado) }
                        .addOnFailureListener { toast(R.string.error_crear_viaje) }
                finish()
                /*
                val intentRestart = Intent(this, MisViajesActivity::class.java)
                intentRestart.putExtra("idViaje", viaje.idViaje)
                intentRestart.putExtra("uidUsuario", viaje.idUsuario)
                startActivity(intentRestart) */

            }else{
                toast(R.string.rellene_campos)

            }

        }
    }




    }



