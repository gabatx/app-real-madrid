package com.funcody.proyectosegundotrimestre.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funcody.proyectosegundotrimestre.AdapterMiniaturas
import com.funcody.proyectosegundotrimestre.model.Imagen
import com.funcody.proyectosegundotrimestre.ImagenAdapter
import com.funcody.proyectosegundotrimestre.adapters.AdapterGaleria
import com.funcody.proyectosegundotrimestre.databinding.FragmentGaleriaBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class GaleriaFragment : Fragment() {

    private lateinit var binding: FragmentGaleriaBinding
    private lateinit var adapterGaleria: AdapterGaleria
    private lateinit var adapterGaleriaMinituras: AdapterMiniaturas

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGaleriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Introduzco los datos del objeto
        var jsonString: String? = null
        try {
            jsonString = requireContext().assets.open("galeria_real_madrid.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        jsonString.let {
            val lista = object : TypeToken<List<Imagen>>() {}.type
            val galeria = Gson().fromJson<List<Imagen>>(jsonString, lista)
            cargaGaleria(galeria)
        }
    }

    private fun cargaGaleria(galeria: List<Imagen>?) {
        galeria?.let {
            // Carga la galeria
            adapterGaleria = AdapterGaleria(galeria)
            binding.viewPagerGaleria.adapter = adapterGaleria
            // Carga las miniaturas
            adapterGaleriaMinituras = AdapterMiniaturas(
                galeria,
                object : ImagenAdapter {
                    override fun clickEnImagen(imagen: Imagen, position: Int) {
                        binding.viewPagerGaleria.currentItem = position
                    }
                }
            )

            binding.recyclerViewMiniaturas.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.recyclerViewMiniaturas.adapter = adapterGaleriaMinituras
        }
    }
}



