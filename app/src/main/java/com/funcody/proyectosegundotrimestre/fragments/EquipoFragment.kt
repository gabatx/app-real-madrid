package com.funcody.proyectosegundotrimestre.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funcody.proyectosegundotrimestre.model.Jugador
import com.funcody.proyectosegundotrimestre.adapters.AdapterJugadores
import com.funcody.proyectosegundotrimestre.databinding.FragmentEquipoBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class EquipoFragment : Fragment() {

    private lateinit var binding: FragmentEquipoBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEquipoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // --------------------------------------------------------------------------------------------------
        // Introduzco los datos del objeto
        var jsonString: String? = null
        try {
            jsonString = requireContext().assets.open("jugadores.json")
                .bufferedReader()
                .use{ it.readText() }
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

        jsonString.let {
            val lista = object : TypeToken<List<Jugador>>() {}.type
            val plantilla = Gson().fromJson<List<Jugador>>(jsonString, lista)
            configuracionReyclerView(plantilla)
        }
        // --------------------------------------------------------------------------------------------------
    }

    private fun configuracionReyclerView(plantilla: List<Jugador>){
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        // El adapter carga el recyclerView
        binding.recyclerView.adapter = AdapterJugadores(plantilla as MutableList<Jugador>)
    }
}