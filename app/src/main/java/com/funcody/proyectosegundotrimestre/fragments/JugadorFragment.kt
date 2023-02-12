package com.funcody.proyectosegundotrimestre.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.funcody.proyectosegundotrimestre.model.Jugador
import com.funcody.proyectosegundotrimestre.databinding.FragmentJugadorBinding
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class JugadorFragment : Fragment() {

    private lateinit var binding: FragmentJugadorBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentJugadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // -------------------------------------------------------------------------------------------------------------
        val jugador: Jugador? = arguments?.getParcelable("jugador")
        rellenaJugador(jugador)
        // Coloca el año actual en el footer
        binding.madridFooter.text = "madrid ${anioActual()}"
    }



    // -------------------------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun rellenaJugador(jugador: Jugador?) {
        binding.jugadorNombre.text = jugador!!.apodo
        binding.jugadorDorsal.text = (jugador.dorsal.toString())
        binding.jugadorPosicion.text = jugador.posicion
        binding.includeDatosPersonales.nombreDatosPersonales.text = jugador.nombre
        binding.includeDatosPersonales.lugarNacimientoDatosPersonales.text = jugador.lugarNacimiento
        binding.includeDatosPersonales.fechaNacimientoDatosPersonales.text = jugador.fechaNacimiento
        binding.includeDatosPersonales.posicionDatosPersonales.text = jugador.posicion
        binding.includeDatosPersonales.pesoDatosPersonales.text = "${jugador.peso} kg"
        binding.includeDatosPersonales.alturaDatosPersonales.text = "${jugador.altura} m"
        if (jugador.twitter != "") {
            binding.botonTwitter.setOnClickListener { abreTwitter(jugador.twitter) }
        } else {
            binding.botonTwitter.visibility = View.GONE
        }

        Glide.with(requireContext())
            .load(jugador.foto)
            .into(binding.jugadorImagenJugador)

        Glide.with(requireContext())
            .load(jugador.fotoPerfil)
            .into(binding.jugadorFotoPerfil)

    }

    private fun abreTwitter(twitter: String) {
        val twitterIntent = Intent(Intent.ACTION_VIEW, Uri.parse(twitter))
        startActivity(twitterIntent)
    }

    // ----------- Genera la fecha formateada --------------------------------------------------------------------------
    @RequiresApi(Build.VERSION_CODES.O)
    private fun anioActual(): String {
        val fecha = Instant.now().toString() // 2022-03-26T21:40:02.258Z
        val zonaHoraria = ZonedDateTime.parse(fecha) // Clase que almacena todos los campos de fecha y hora,
        return zonaHoraria.format(DateTimeFormatter.ofPattern("uuuu")) // Retornamos con el formato deseado (dd.MMMM.uuuu HH:mm)
    }
}