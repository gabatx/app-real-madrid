package com.funcody.proyectosegundotrimestre

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.funcody.proyectosegundotrimestre.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // Guardar valor usuario guardado
    private lateinit var preferencias: SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // --------------------------------------------------------------------------------------------------
        // Barra superior e inferior transparente
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // --------------------------------------------------------------------------------------------------
        // Inicializo las preferencias para guardar datos de login y de usuario
        preferencias = getSharedPreferences("mispreferencias", MODE_PRIVATE)
        // Si la sesión está abierta obvia la pantalla de login
        if (preferencias.getBoolean("logueado", false)) {startActivity(Intent(this, MainActivity::class.java));finish()}
        // --------------------------------------------------------------------------------------------------
        binding.botonIniciarSesion.setOnClickListener {
            val valorUsuario = binding.inputEditUsuario.text.toString()
            val valorContrasenia = binding.inputEditContrasenia.text.toString()

            if (camposRellenados(valorUsuario, valorContrasenia) && validarUsuario(valorUsuario, valorContrasenia)
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.botonInvitado.setOnClickListener {
            introduceDatosValidacionPreferencias("invitado")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    // --------------------------------------------------------------------------------------------------
    // Si los campos están vacíos muestra un de error y devuelve falso. Si los campos están completados devuelve true
    private fun camposRellenados(valorUsuario: String, valorContrasenia: String): Boolean {

        when {
            valorUsuario == "" && valorContrasenia == "" -> {
                binding.inputUsuario.error = resources.getText(R.string.input_vacio)
                binding.inputContrasenia.error = resources.getText(R.string.input_vacio)
                return false
            }
            valorUsuario == ""                           -> {
                binding.inputContrasenia.error = null
                binding.inputUsuario.error = resources.getText(R.string.input_vacio)
                return false
            }
            valorContrasenia == ""                       -> {
                binding.inputUsuario.error = null
                binding.inputContrasenia.error = resources.getText(R.string.input_vacio)
                return false
            }
            // Si los dos campos están rellenos eliminar los errores si acaso los hay.
            else                                         -> {
                binding.inputUsuario.error = null
                binding.inputContrasenia.error = null
                return true
            }
        }
    }

    private fun validarUsuario(valorUsuario: String, valorContrasenia: String): Boolean {
        return when {
            valorUsuario == "aficionado" && valorContrasenia == "123456"  -> introduceDatosValidacionPreferencias(valorUsuario)
            valorUsuario == "jugador" && valorContrasenia == "qwerasdf"   -> introduceDatosValidacionPreferencias(valorUsuario)
            valorUsuario == "directivo" && valorContrasenia == "asdfasdf" -> introduceDatosValidacionPreferencias(valorUsuario)
            valorUsuario == "invitado"                                    -> introduceDatosValidacionPreferencias(valorUsuario)
            else                                                          -> {
                dialogContraseniaIncorrecta()
                false
            }
        }
    }

    private fun introduceDatosValidacionPreferencias(valorUsuario: String): Boolean {
        // Importante crear el objeto antes, no colocar directamente preferencias.edit()
        val editor = preferencias.edit()

        if (valorUsuario == "invitado") {
            editor.putBoolean("logueado", false)
            editor.putString("usuario", "invitado")
            editor.apply()
        } else {
            editor.putBoolean("logueado", true)
            editor.putString("usuario", valorUsuario)
            editor.apply()
        }
        return true
    }

    @SuppressLint("ResourceAsColor")
    private fun dialogContraseniaIncorrecta() {

        val builder = AlertDialog.Builder(this, R.style.temaAlertDialog)
        builder.setTitle("Error")
        builder.setMessage("Usuario y contraseña incorrectos. Vuelve a intentarlo")
        builder.setPositiveButton(R.string.cerrar, null)

        val dialog = builder.create()
        dialog.show()
    }
}