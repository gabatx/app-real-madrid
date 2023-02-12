package com.funcody.proyectosegundotrimestre

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.funcody.proyectosegundotrimestre.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var preferencias: SharedPreferences
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: androidx.navigation.NavController
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // --------------------------------------------------------------------------------------------------
        setSupportActionBar(binding.toolbar)
        // --------------------------------------------------------------------------------------------------
        val drawerLayout: DrawerLayout = binding.drawerlayout
        val navView: NavigationView = binding.navigationview
        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_fragment_inicio,
                R.id.nav_fragment_equipo,
                R.id.nav_fragment_galeria,
                R.id.nav_fragment_contacto
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // --------------------------------------------------------------------------------------------------
        preferencias = getSharedPreferences("mispreferencias", MODE_PRIVATE)
        val usuario = preferencias.getString("usuario", "no llega nada").toString()
        // Accedemos a un elemento de la cabecera:
        // Cambiamos el usuario
        binding.navigationview.getHeaderView(0).findViewById<TextView>(R.id.cabecera_usuario).text = usuario
        // Introducimos la fecha actual
        binding.navigationview.getHeaderView(0).findViewById<TextView>(R.id.cabecera_fecha).text = fechaActual()
        // --------------------------------------------------------------------------------------------------
    }

    // Genera la fecha formateada
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fechaActual(): String {
        val fecha = Instant.now().toString() // 2022-03-26T21:40:02.258Z
        val zonaHoraria = ZonedDateTime.parse(fecha) // Clase que almacena todos los campos de fecha y hora,
        return zonaHoraria.format(DateTimeFormatter.ofPattern("dd.MM.uu")) // Retornamos con el formato deseado (dd.MMMM.uuuu HH:mm)
    }


    //  Cierra la sesión y reinicia las variables logueado y usuario.
    private fun cerrarSesion() {
        val editor = preferencias.edit()
        editor.putBoolean("logueado", false)
        editor.putString("usuario", "")
        editor.apply()
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }

    // Se llama a este método cada vez que el usuario elige navegar hacia arriba dentro de la jerarquía de actividad de su aplicación desde la barra de acción.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    // Al pulsar hacia atrás
    override fun onBackPressed() {
       when {
           binding.drawerlayout.isDrawerOpen(GravityCompat.START) -> binding.drawerlayout.closeDrawer(GravityCompat.START)
           //navController.popBackStack() ->  findNavController(R.id.fragmentContainerView).navigate(R.id.action_jugadorFragment_to_nav_fragment_equipo)
           supportActionBar!!.title.toString() == "Jugador"       -> findNavController(R.id.fragmentContainerView).navigate(R.id.action_jugadorFragment_to_nav_fragment_equipo)
           else                                                   -> dialogSalirAplicacion()
       }
    }

    // Infla en menú superior que hemos creado en res/menu/menu_superior
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return true
    }

    // La función sirve de evento al pulsar un item del toolbar superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.boton_cerrar_sesion) {
            dialogSalirAplicacion()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ResourceAsColor")
    private fun dialogSalirAplicacion() {
        val builder = AlertDialog.Builder(this, R.style.temaAlertDialog)
        builder.setMessage("¿Desea salir de la aplicación?")
        builder.setPositiveButton(R.string.si) { _, _ ->
            cerrarSesion()
        }
        builder.setNegativeButton(R.string.no, null)
        val dialog = builder.create()
        dialog.show()
    }
}