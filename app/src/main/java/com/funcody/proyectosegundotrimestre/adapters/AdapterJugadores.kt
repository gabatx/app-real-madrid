package com.funcody.proyectosegundotrimestre.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.funcody.proyectosegundotrimestre.model.Jugador
import com.funcody.proyectosegundotrimestre.R
import com.funcody.proyectosegundotrimestre.databinding.HolderJugadorBinding

class AdapterJugadores(
        private val listaJugadores: MutableList<Jugador>,
) : RecyclerView.Adapter<AdapterJugadores.ViewHolderJugador>() {

    inner class ViewHolderJugador(val binding: HolderJugadorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJugador {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderJugadorBinding.inflate(layoutInflater, parent, false)
        return ViewHolderJugador(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderJugador, position: Int) {
        val jugador = listaJugadores[position]

        holder.binding.holderJugadorNum.text = jugador.dorsal.toString()
        holder.binding.holderJugadorNombre.text = jugador.apodo
        holder.binding.holderJugadorPosicion.text = jugador.posicion
        asignaColorPosicion(jugador.posicion, holder.binding.fondoRotuloJugador)
        val imagen = if (validaUrlImagen(jugador.foto)) jugador.foto else "https://funcody.com/lib/img/imgProyectoAndroid/jugadores/Noexiste.png"
        Glide
            .with(holder.binding.root.context)
            .load(imagen)
            .into(holder.binding.holderJugadorFoto)

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("jugador" to jugador)
            holder.itemView.findNavController().navigate(R.id.action_nav_fragment_equipo_to_jugadorFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return listaJugadores.size
    }

    private fun validaUrlImagen(valor: String): Boolean {
        val expRegValidacion = Regex("^https?:\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*\$")
        return expRegValidacion.matches(valor)
    }

    private fun asignaColorPosicion(posicion: String, vista: TextView){
        if (posicion == "Portero") vista.setBackgroundResource(R.drawable.degradado_fondo_datos_holder_portero)
        if (posicion == "Defensa") vista.setBackgroundResource(R.drawable.degradado_fondo_datos_holder_defensa)
        if (posicion == "Centrocampista") vista.setBackgroundResource(R.drawable.degradado_fondo_datos_centrocampista)
        if (posicion == "Delantero") vista.setBackgroundResource(R.drawable.degradado_fondo_datos_holder_delantero)
    }
}