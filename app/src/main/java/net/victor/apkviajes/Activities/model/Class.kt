package net.victor.apkviajes.Activities.model

import java.util.*


data class Viaje(var idUsuario:String="", var lugar:String = "", var descripcion : String = "", var latitud : String = "", var longitud:String = "", var fechaInicio:String = "", var idViaje: String = "")
data class Evento(var idUsuario: String="", var lugar: String="",var descripcion: String = "", var latitud: String ="", var longitud: String ="", var fechaEvento:String="", var idViaje :String = "" )
