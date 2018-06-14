package net.victor.apkviajes.Activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.row_mis_viajes.view.*
import net.victor.apkviajes.Activities.Views.EventosViajesActivity
import net.victor.apkviajes.Activities.Views.MisViajesActivity
import net.victor.apkviajes.Activities.model.Evento
import net.victor.apkviajes.Activities.model.Viaje
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class CustomAdapterViajes(val context: Context,
                             val layout: Int,
                             val dataList: List<Viaje>
): RecyclerView.Adapter<CustomAdapterViajes.ViewHolder>() {
    companion object {
        private val REQUEST_DETALLE = 0
    }


    private lateinit var mAuth: FirebaseAuth

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
            itemView.tvLugarViaje.text = dataItem.lugar
            itemView.tvDescripcionViaje.text = dataItem.descripcion
            itemView.tvCreador.text = dataItem.creador.toString()
            itemView.tvFechaViaje.text = dataItem.fechaInicio
            itemView.setOnClickListener({
                onItemClick(dataItem)
            })

            itemView.setOnLongClickListener({
                onLongItemClick(dataItem)

            })
        }
    }


    private fun onItemClick(dataItem: Viaje) {
        val intent = Intent(context as Activity, EventosViajesActivity::class.java)
        intent.putExtra("lugar", dataItem.lugar)
        intent.putExtra("creador", dataItem.creador)
        intent.putExtra("uidUsuario", dataItem.idUsuario)
        intent.putExtra("uidUsuario", dataItem.idUsuario)
        intent.putExtra("idViaje", dataItem.idViaje)
        context.startActivity(intent)

    }

    private fun onLongItemClick(dataItem: Viaje): Boolean {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser?.uid == dataItem.idUsuario) {
            var db = FirebaseFirestore.getInstance()
            context.alert("¿Quieres eliminar este evento?") {
                title = "Confirm"
                yesButton {
                    db.collection("viajes").document(dataItem.idViaje).delete()
                    (context as MisViajesActivity).recreate()


                }
                noButton { }
            }.show()

            return true
        } else {
            return false
        }

    }
}