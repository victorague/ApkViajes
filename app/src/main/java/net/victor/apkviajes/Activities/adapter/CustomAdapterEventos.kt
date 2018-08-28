package net.victor.apkviajes.Activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.row_evento.view.*
import net.victor.apkviajes.Activities.Views.DetalleEventoActivity
import net.victor.apkviajes.Activities.Views.EventosViajesActivity
import net.victor.apkviajes.Activities.Views.MisViajesActivity
import net.victor.apkviajes.Activities.model.Evento
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class CustomAdapterEventos(val context: Context,
                     val layout: Int,
                     val dataList: List<Evento>
): RecyclerView.Adapter<CustomAdapterEventos.ViewHolder>() {
    companion object {
        private val REQUEST_DETALLE=0
    }

    //private lateinit var recetaException : DatabaseError
    //private lateinit var unit : Unit
    private lateinit var mAuth: FirebaseAuth



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
            itemView.tvFechaViaje.text = dataItem.fechaEvento
            itemView.tvLugarViaje.text = dataItem.lugar
            itemView.setOnClickListener({
                onItemClick(dataItem)

            })

            itemView.setOnLongClickListener({
                onLongItemClick(dataItem)

            })      }
    }


        private fun onItemClick(dataItem: Evento):Boolean {
            val intent = Intent(context as Activity, DetalleEventoActivity::class.java)
            intent.putExtra("lugar", dataItem.lugar)
            intent.putExtra("idUsuario", dataItem.idUsuario)
            intent.putExtra("idViaje", dataItem.idEvento)
            intent.putExtra("latitud", dataItem.latitud)
            intent.putExtra("longitud", dataItem.longitud)
            intent.putExtra("descripcion", dataItem.descripcion)
            intent.putExtra("fecha",dataItem.fechaEvento)
            context.startActivity(intent)

            return true
        }

    private fun onLongItemClick(dataItem: Evento): Boolean {
        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser?.uid == dataItem.idUsuario){
            var db = FirebaseFirestore.getInstance()
            context.alert("¿Quieres eliminar este evento?") {
                title = "Confirm"
                yesButton {
                    db.collection("eventos").document(dataItem.idEvento).delete()
                    (context as EventosViajesActivity).recreate()


                }
                noButton { }
            }.show()

            return true
        }else{
            return false
        }

    }
    }
