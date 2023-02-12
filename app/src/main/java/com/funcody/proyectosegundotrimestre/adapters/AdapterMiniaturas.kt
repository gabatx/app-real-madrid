package com.funcody.proyectosegundotrimestre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.funcody.proyectosegundotrimestre.databinding.HolderGaleriaMiniaturaBinding
import com.funcody.proyectosegundotrimestre.model.Imagen

// Creo una interfaz con una función que le paso una tarea y la posición del item pulsado
interface ImagenAdapter {
    fun clickEnImagen(imagen: Imagen, position: Int)
}

class AdapterMiniaturas(
        val listaImagenes: List<Imagen>,
        val imagenAdapter: ImagenAdapter  // Añado como argumento una variable de tipo ImagenAdapter que recoge a la interfaz
    ) : RecyclerView.Adapter<AdapterMiniaturas.ViewHolderMiniaturas>() {

    class ViewHolderMiniaturas(val binding: HolderGaleriaMiniaturaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMiniaturas {
        val binding = HolderGaleriaMiniaturaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderMiniaturas(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderMiniaturas, position: Int) {

        val imagenActual = listaImagenes[position]
        val imagen = if (validaUrlImagen(imagenActual.foto)) imagenActual.foto else "https://funcody.com/lib/img/imgProyectoAndroid/jugadores/Noexiste.png"
        Glide
            .with(holder.binding.root.context)
            .load(imagen)
            .into(holder.binding.imagenGaleriaMiniatura)

        holder.itemView.setOnClickListener {
            imagenAdapter.clickEnImagen(imagenActual, position)
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