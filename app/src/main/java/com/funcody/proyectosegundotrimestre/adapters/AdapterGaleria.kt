package com.funcody.proyectosegundotrimestre.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.funcody.proyectosegundotrimestre.model.Imagen
import com.funcody.proyectosegundotrimestre.databinding.HolderGaleriaBinding

class AdapterGaleria(private val listaImagenes: List<Imagen>) : RecyclerView.Adapter<AdapterGaleria.ViewHolderGaleria>() {

    class ViewHolderGaleria(val binding: HolderGaleriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGaleria {
        val binding = HolderGaleriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderGaleria(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderGaleria, position: Int) {

        val imagenActual = listaImagenes[position]
        val imagen = if (validaUrlImagen(imagenActual.foto)) imagenActual.foto else "https://funcody.com/lib/img/imgProyectoAndroid/jugadores/Noexiste.png"
        Glide
            .with(holder.binding.root.context)
            .load(imagen)
            .into(holder.binding.imagenGaleria)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listaImagenes.size
    }

    private fun validaUrlImagen(valor: String): Boolean {
        val expRegValidacion = Regex("^https?:\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*\$")
        return expRegValidacion.matches(valor)
    }

}

