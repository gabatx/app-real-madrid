package com.funcody.proyectosegundotrimestre.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Jugador(
        val id:Int,
        val apodo: String,
        val nombre: String,
        val posicion: String,
        @SerializedName("lugar de nacimiento")
        val lugarNacimiento: String,
        @SerializedName("fecha de nacimiento")
        val fechaNacimiento: String,
        val dorsal: Byte,
        val altura: Float,
        val peso: Float,
        val palmares: Palmares,
        val trayectoria: ArrayList<String>,
        val foto: String,
        val fotoPerfil: String,
        val twitter: String
        ) : Parcelable

@Parcelize
data class Palmares(
        @SerializedName("Champions League")
        val championLeague : String = "",
        @SerializedName("Copa Mundial de Clubes")
        val copaMundialClubes : String  = "",
        @SerializedName("Supercopa de Europa")
        val supercopaEuropa : String  = "",
        @SerializedName("Liga de España")
        val LigaEspania : String  = "",
        @SerializedName("Medalla de Plata JJOO de Tokio")
        val medallaJJOO : String  = "",
        @SerializedName("Campeonato de Europa sub-19")
        val campeonatoSub19 : String  = "",
): Parcelable



