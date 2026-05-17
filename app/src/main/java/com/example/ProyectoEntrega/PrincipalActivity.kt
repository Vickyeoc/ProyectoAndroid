package com.example.ProyectoEntrega

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.incio)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
val medio= findViewById<ImageButton>(R.id.medio)
val facil=findViewById<ImageButton>(R.id.facil)
val dificil =findViewById<ImageButton>(R.id.dificil)
val editor= findViewById<EditText>(R.id.editText)
val aceptarNombre= findViewById<ImageButton>(R.id.aceptarInicio)
val nombre= findViewById<TextView>(R.id.nombre)
val saludar =findViewById<TextView>(R.id.textView5)
val ayuda =findViewById<ImageButton>(R.id.AyudaBoton)

medio.setOnClickListener {
   val i= Intent(this, MainActivity::class.java)
    i.putExtra("dificultad",1)
    startActivity(i) }

facil.setOnClickListener {
    val i= Intent(this, MainActivity::class.java)
    i.putExtra("dificultad",0)
    startActivity(i) }

dificil.setOnClickListener {
    val i= Intent(this, MainActivity::class.java)
    i.putExtra("dificultad",2)
    startActivity(i) }
aceptarNombre.setOnClickListener {
    if(editor.textSize>1){
    nombre.text= editor.text.toString()
    editor.setText("")
    nombre.visibility= View.VISIBLE
    saludar.visibility=View.VISIBLE}else
    {
        Toast.makeText(this, "El nombre de usuario tiene que tener al menos 1 caracter", Toast.LENGTH_SHORT).show()
    }
}
        ayuda.setOnClickListener {
            val i=Intent(this, Ayuda::class.java)
            startActivity(i)
        }
    }
}