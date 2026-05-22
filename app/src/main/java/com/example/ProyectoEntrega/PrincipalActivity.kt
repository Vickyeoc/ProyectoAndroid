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
var texto=String()
var b= false

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
b= false

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
    if(editor.text.isNotBlank()){
    texto=editor.text.toString()
    nombre.text= editor.text.toString()
    editor.setText("")
    nombre.visibility= View.VISIBLE
    saludar.visibility=View.VISIBLE
    b=true}else
    {
        Toast.makeText(this, R.string.carac, Toast.LENGTH_SHORT).show()
    }
}
        ayuda.setOnClickListener {
            val i=Intent(this, Ayuda::class.java)
            startActivity(i)
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("texto", texto)
        outState.putBoolean("vista", b)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        b=savedInstanceState.getBoolean("vista")
        findViewById<TextView>(R.id.nombre).text= savedInstanceState.getString("texto")
        if(b) {
            findViewById<TextView>(R.id.nombre).visibility= View.VISIBLE
            findViewById<TextView>(R.id.textView5).visibility=View.VISIBLE}
    }
}