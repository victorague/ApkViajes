package net.victor.apkviajes.Activities.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detalle_evento.*
import net.victor.apkviajes.R


class DetalleEventoActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var coordenadas:LatLng




    override fun onMapReady(map: GoogleMap?) {
        var intent = getIntent().extras
        var latitud  = intent.getString("latitud")
        var longitud = intent.getString("longitud")
        var latitudDouble = latitud.toDouble()
        var longitudDouble = longitud.toDouble()
        var latlong: LatLng
        latlong = LatLng(latitudDouble,longitudDouble)


        tvDescripcionEventoDetalle.text = intent.getString("descripcion")
        tvLugarEventoDetalle.text = intent.getString("lugar")
        tvFechaEventoDetalle.text = intent.getString("fecha")



        map?.addMarker(MarkerOptions()
                .position(LatLng(latitudDouble,longitudDouble))
                .title("Lugar del evento"))



        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong , 7F))


        //informacion sobre elementos del mapa https://developers.google.com/maps/documentation/android-api/views?hl=es-419

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)
        var intent = getIntent().extras
         var latitud  = intent.getString("latitud")
         var longitud = intent.getString("longitud")
        val mapFragment = fragmentManager
                .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

    }

    }



