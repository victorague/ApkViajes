package net.victor.apkviajes.Activities.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_buscar_viajes.*
import net.victor.apkviajes.R
import org.jetbrains.anko.toast


class BuscarViajeActivity : AppCompatActivity() {

    private  lateinit var lugar:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_viajes)
        lugar = ""

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPlaceSelected(place: Place) {
               lugar = place.name.toString()
            }


        })



        btnBuscar.setOnClickListener {
            if(lugar == ""){
                toast(R.string.elija_lugar)
            }else{
            val intent = Intent(this, ResultadoBusquedaActivity::class.java)
            intent.putExtra("lugarBuscado", lugar)
            startActivity(intent)
        }}
    }
}
