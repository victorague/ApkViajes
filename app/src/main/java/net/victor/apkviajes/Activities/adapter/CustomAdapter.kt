package net.victor.apkviajes.Activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_mis_viajes.view.*
import net.victor.apkviajes.Activities.Views.EventosViajesActivity
import net.victor.apkviajes.Activities.model.Viaje


class CustomAdapterViajes(val context: Context,
                             val layout: Int,
                             val dataList: List<Viaje>
): RecyclerView.Adapter<CustomAdapterViajes.ViewHolder>() {
    companion object {
        private val REQUEST_DETALLE = 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {


        fun bind(dataItem: Viaje, position: Int) {
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvLugar.text = dataItem.lugar
            itemView.tvDescripcion.text = dataItem.descripcion
            itemView.tvFechaEvento.text = dataItem.fechaInicio
            itemView.setOnClickListener({
                onItemClick(dataItem)
            })
        }

    }


    private fun onItemClick(dataItem: Viaje) {
        val intent = Intent(context as Activity, EventosViajesActivity::class.java)
        intent.putExtra("lugar", dataItem.lugar)
        intent.putExtra("idUsuario", dataItem.idUsuario)
        intent.putExtra("idViaje", dataItem.idViaje)
        context.startActivity(intent)

    }

}