package net.victor.apkviajes.Activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_mis_viajes.view.*
import net.victor.apkviajes.Activities.Views.EventosViajeBuscado
import net.victor.apkviajes.Activities.model.Viaje
import net.victor.apkviajes.ActivityConfirmacion


class CustomAdapterBusqueda(val context: Context,
                           val layout: Int,
                           val dataList: List<Viaje>
): RecyclerView.Adapter<CustomAdapterBusqueda.ViewHolder>() {
    companion object {
        private val REQUEST_DETALLE=0
    }

    //private lateinit var recetaException : DatabaseError
    //private lateinit var unit : Unit



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {

        fun bind(dataItem: Viaje, position: Int) {
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvDescripcionViaje.text = dataItem.descripcion
            itemView.tvFechaViaje.text = dataItem.fechaInicio
            itemView.tvLugarViaje.text = dataItem.lugar
            itemView.tvCreador.text = dataItem.creador.toString()
            itemView.setOnClickListener({
                onItemClick(dataItem)
            })
        }
    }


    private fun onItemClick(dataItem: Viaje) {
        val intent = Intent(context as Activity, EventosViajeBuscado::class.java)
        intent.putExtra("lugar", dataItem.lugar)
        intent.putExtra("idUsuario", dataItem.idUsuario)
        intent.putExtra("idViaje", dataItem.idViaje)
        intent.putExtra("latitud", dataItem.latitud)
        intent.putExtra("longitud", dataItem.longitud)
        intent.putExtra("creador", dataItem.creador)
        intent.putExtra("descripcion", dataItem.descripcion)
        context.startActivity(intent)

    }
}
