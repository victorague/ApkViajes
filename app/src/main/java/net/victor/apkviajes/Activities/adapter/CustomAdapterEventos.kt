package net.victor.apkviajes.Activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_evento.view.*
import net.victor.apkviajes.Activities.Views.DetalleEventoActivity
import net.victor.apkviajes.Activities.model.Evento


/**
 * Created by diurno on 19/02/2018.
 */
class CustomAdapterEventos(val context: Context,
                     val layout: Int,
                     val dataList: List<Evento>
): RecyclerView.Adapter<CustomAdapterEventos.ViewHolder>() {
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

        fun bind(dataItem: Evento, position: Int) {
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvDescripcion.text = dataItem.descripcion
            itemView.tvFecha.text = dataItem.fechaEvento
            itemView.tvLugar.text = dataItem.lugar
            itemView.setOnClickListener({
                onItemClick(dataItem)
            })
        }
    }


        private fun onItemClick(dataItem: Evento) {
            val intent = Intent(context as Activity, DetalleEventoActivity::class.java)
            intent.putExtra("lugar", dataItem.lugar)
            intent.putExtra("idUsuario", dataItem.idUsuario)
            intent.putExtra("idViaje", dataItem.idViaje)
            intent.putExtra("latitud", dataItem.latitud)
            intent.putExtra("longitud", dataItem.longitud)
            intent.putExtra("descripcion", dataItem.descripcion)
            context.startActivity(intent)

        }
    }
