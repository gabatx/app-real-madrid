package com.funcody.proyectosegundotrimestre.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.funcody.proyectosegundotrimestre.R
import com.funcody.proyectosegundotrimestre.databinding.FragmentContactoBinding


class ContactoFragment : Fragment() {

    private lateinit var binding: FragmentContactoBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentContactoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EVENTOS DE CLICK
        binding.botonLocalizacion.setOnTouchListener { _, event ->  pulsacion(event, binding.botonLocalizacion, "localizacion"); true }
        binding.botonEmail.setOnTouchListener { _, event -> pulsacion(event, binding.botonEmail, "email"); true }
        binding.botonPhone.setOnTouchListener { _, event -> pulsacion(event, binding.botonPhone, "telefono"); true }
        binding.botonWhatsapp.setOnTouchListener { _, event -> pulsacion(event, binding.botonWhatsapp, "whatsapp"); true}

        // FORMULARIO DE CONTACTO
        binding.botonEnviarFormulario.setOnClickListener {
            if (validarFormulario()) confirmarEnvio()
        }
    }



    // Función que gestiona la pulsación de los botones al pulsar y al soltar
    private fun pulsacion(event: MotionEvent?, botonLocalizacion: CardView, clave: String) {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                botonLocalizacion.elevation = 1f
            }
            MotionEvent.ACTION_UP   -> {
                botonLocalizacion.elevation = 4f
                when (clave) {
                    "localizacion" -> intentUbicacion()
                    "email"        -> intentEmail()
                    "telefono"     -> intentTelefono()
                    "whatsapp"     -> intentWhatsapp()
                }
            }
        }
    }

    private fun intentWhatsapp() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=34637744494")
        startActivity(intent)
    }

    private fun intentTelefono() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:+34913984300")
        startActivity(intent)
    }

    private fun intentEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("atencionpublico@corp.realmadrid.com"))
        startActivity(emailIntent)
    }

    private fun intentUbicacion() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo: 40.452663421631,-3.688530921936?q=Estadio+Santiago+Bernabeu,+Madrid")
        startActivity(intent)
    }

    private fun validarFormulario(): Boolean {
        val nombre = binding.textInputNombre.text.toString()
        val email = binding.textInputEmail.text.toString()
        val telefono = binding.textInputTelefono.text.toString()
        val mensaje = binding.textInputMensaje.text.toString()
        val privacidad = binding.checkBoxPrivacidad.isChecked

        return if (nombre.isNotEmpty() && email.isNotEmpty() && telefono.isNotEmpty() && mensaje.isNotEmpty() && validarEmail(email) && privacidad) {
            true
        } else {
            if (nombre.isEmpty()) binding.textInputNombre.error = "Introduce tu nombre"
            if (email.isEmpty()) binding.textInputEmail.error = "Introduce tu email" else if (!validarEmail(email)) binding.textInputEmail.error = "Email no válido"
            if (telefono.isEmpty()) binding.textInputTelefono.error = "Introduce tu teléfono"
            if (mensaje.isEmpty()) binding.textInputMensaje.error = "Introduce tu mensaje"
            if (privacidad.not()) binding.checkBoxPrivacidad.error = "Acepta la política de privacidad"
            false
        }
    }

    private fun validarEmail(valor: String): Boolean {
        val expRegValidacion = Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
        return expRegValidacion.matches(valor)
    }

    @SuppressLint("ResourceAsColor")
    private fun confirmarEnvio() {
        val builder = AlertDialog.Builder(requireContext(), R.style.temaAlertDialog)
        builder.setMessage("¿Deseas enviar los datos de contacto?")
        builder.setPositiveButton(R.string.si) { _, _ ->
            envioFormulario()
        }
        builder.setNegativeButton(R.string.no, null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun envioFormulario() {
        val direccionEmail = arrayOf("info@escuelaestech.es", "secretaria@escuelaestech.es")
        val asunto = "Formulario de contacto:"
        val texto = """
            Nombre: ${binding.textInputNombre.text}
            Email: ${binding.textInputEmail.text}
            Teléfono: ${binding.textInputTelefono.text}
            Mensaje: ${binding.textInputMensaje.text}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, direccionEmail)
            putExtra(Intent.EXTRA_SUBJECT, asunto)
            putExtra(Intent.EXTRA_TEXT, texto)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

