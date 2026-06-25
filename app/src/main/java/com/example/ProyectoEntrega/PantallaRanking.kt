package com.example.ProyectoEntrega

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import androidx.core.content.edit
data class RegistroRanking(val nombre: String, val puntaje: Int)

class PantallaRanking : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_ranking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /////////////CONSTANTES
        val valorAlto =99999999
        val volver= findViewById<ImageButton>(R.id.volver)
        val pref = getSharedPreferences("RankingDB", Context.MODE_PRIVATE)
        val ids= arrayOf(
            findViewById<TextView>(R.id.primero),
            findViewById<TextView>(R.id.segundo),
            findViewById<TextView>(R.id.tercero),
            findViewById<TextView>(R.id.cuarto),
            findViewById<TextView>(R.id.quinto)
        )
        val vector = Array(5) { RegistroRanking("", valorAlto) }

        ////GUARDO EN EL VECTOR LO QUE ESTABA GUARDADO EN EL DISCO
        for (i in 0..4) {
            val nombreGuardado = pref.getString("nombre_$i", "") ?: ""
            val puntajeGuardado = pref.getInt("puntaje_$i", valorAlto)
            vector[i] = RegistroRanking(nombreGuardado, puntajeGuardado)
        }

///////Se muestra en el puesto lo que esta guardado en el disco, si el puesto tiene a alguien guardado se cambia por ese alguien
        for (i in 0..4){
            if(vector[i].puntaje!= valorAlto){
                ids[i].text=" ${i+1}: ${vector[i].nombre}, ${vector[i].puntaje}"
            }
        }

        //volves a pantalla principal
        volver.setOnClickListener { finish() }
    }
}