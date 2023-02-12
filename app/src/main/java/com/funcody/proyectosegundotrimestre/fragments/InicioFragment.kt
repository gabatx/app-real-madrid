package com.funcody.proyectosegundotrimestre.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.funcody.proyectosegundotrimestre.R
import com.funcody.proyectosegundotrimestre.databinding.FragmentInicioBinding


class InicioFragment : Fragment() {
    private lateinit var preferencias: SharedPreferences
    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --------------------------------------------------------------------------------------------------
        binding.imagenJugadorAleatorio.setImageDrawable(ContextCompat.getDrawable(requireContext(), imagenAleatoria()))
        // --------------------------------------------------------------------------------------------------
        // Para llamar a las variables guardadas en local desde un fragment se hace así:
        preferencias = requireActivity().getSharedPreferences("mispreferencias", Context.MODE_PRIVATE)
        val usuario = preferencias.getString("usuario", "no llega nada").toString()
        val logueado = preferencias.getBoolean("logueado", false)
        if (!logueado) {
            binding.fragInicioContentBienvenido.visibility = View.GONE
        } else {
            binding.fragInicioUsuario.text = usuario
        }
        // --------------------------------------------------------------------------------------------------
        var contador = 0
        binding.fragEscudoMadrid.setOnClickListener {
            contador++
            if (contador == 10) {
                if (!preferencias.getBoolean("regalo_entregado", false)) {
                    dialogHuevoPascua()
                }
            }
        }




        // -- FIN --
    }

    // Devuelve una imagen aleatoria
    private fun imagenAleatoria(): Int {
        val arrayJugadores = arrayListOf(
            R.drawable.jugador_alaba,
            R.drawable.jugador_asensio,
            R.drawable.jugador_benzema,
            R.drawable.jugador_camavinga,
            R.drawable.jugador_carvajal,
            R.drawable.jugador_courtois,
            R.drawable.jugador_kroos,
            R.drawable.jugador_mendy,
            R.drawable.jugador_militao,
            R.drawable.jugador_modric,
            R.drawable.jugador_vinicius,
        )
        return arrayJugadores.random()
    }


    // --------------------------------------------------------------------------------------------------
    private fun dialogHuevoPascua() {
        val builder = AlertDialog.Builder(requireContext(), R.style.temaAlertDialog)
        builder.setTitle("Elige un regalo")
        builder.setItems(R.array.regalos) { _, index ->
            val arrayRegalos = resources.getStringArray(R.array.regalos)
            recogeRegalo(arrayRegalos[index])
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun recogeRegalo(regalo: String?) {

        val codigo = when (regalo) {
            "Entrada Vip"               -> "25EG65"
            "Cena restaurante"          -> "66GSDR"
            "Camiseta Vinicius firmada" -> "86SSDF"
            "Cuota socio gratis"        -> "87DOS"
            else                        -> "11111"
        }

        val builder = AlertDialog.Builder(requireContext(), R.style.temaAlertDialog)
        builder.setTitle("¡Enhorabuena!")
        builder.setMessage("Has ganado el premio ${regalo!!.uppercase()}. Pasa por nuestras oficinas con el código $codigo y te lo entregaremos")
        builder.setPositiveButton(R.string.cerrar, null)
        val dialog = builder.create()
        dialog.show()

        // Marco el regalo como entregado.
        val editor = preferencias.edit()
        editor.putBoolean("regalo_entregado", true)
        editor.apply()
    }
}