package net.victor.apkviajes.Activities.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_mis_viajes.view.*
import net.victor.apkviajes.Activities.model.Viaje
import org.jetbrains.anko.alert


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
            itemView.tvFecha.text = dataItem.fechaInicio
            itemView.setOnClickListener({
                onItemClick(dataItem)
            })
        }

    }


    private fun onItemClick(dataItem: Viaje) {

    }

}