package com.example.ProyectoEntrega

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    var cant=1
    var codigoSecreto= IntArray(4){ (0..3).random() }
    var intento=IntArray(4){(-1)}
    var correcto=0
    var noCorrecto=0
    var i =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//variables
val botonRojo = findViewById<ImageButton>(R.id.rojo)
val botonAzul = findViewById<ImageButton>(R.id.azul)
val botonVerde = findViewById<ImageButton>(R.id.verde)
val botonAmarillo = findViewById<ImageButton>(R.id.amarillo)
val gano =findViewById<TextView>(R.id.gano)
val e= findViewById< ImageButton>(R.id.enviar)
val borrar= findViewById<ImageButton>(R.id.eliminar)
val reiniciar = findViewById<ImageButton>(R.id.reiniciar)

//Creo vector de imagenes
var vectorImagenes = arrayOf(findViewById<ImageView>(R.id.btn0),
        findViewById<ImageView>(R.id.btn1),
        findViewById<ImageView>(R.id.btn2),
        findViewById<ImageView>(R.id.btn3))
var vectorAnterior =arrayOf(findViewById<ImageView>(R.id.anteriorbtn0),
        findViewById<ImageView>(R.id.anteriorbtn1),
        findViewById<ImageView>(R.id.anteriorbtn2),
        findViewById<ImageView>(R.id.anteriorbtn3))


//funcion para reiniciar
        fun retry () {
            cant = 1
            i = 0
            vectorImagenes=reiniciarVector(vectorImagenes)
            vectorAnterior=reiniciarVector(vectorAnterior)
            findViewById<TextView>(R.id.cantAcertados).text = 0.toString()
            findViewById<TextView>(R.id.num).text = cant.toString()
            findViewById<TextView>(R.id.cantNoAcierto).text = 0.toString()
            codigoSecreto = IntArray(4) { (0..3).random() }
            intento= IntArray(4){(-1)}

        }

//boton envio
e.setOnClickListener {
    correcto= 0
    noCorrecto=0
    if(i==4){
         if(gano.isVisible){ gano.visibility= View.GONE}
             cant++
             i=0
             for (j in 0..3) { vectorAnterior[j].setImageDrawable(vectorImagenes[j].drawable) }
             correcto=correctos(codigoSecreto,intento)[0]
             noCorrecto=correctos(codigoSecreto,intento)[1]
             findViewById<TextView>(R.id.num).text=cant.toString()
             findViewById<TextView>(R.id.cantAcertados).text= correcto.toString()
             findViewById<TextView>(R.id.cantNoAcierto).text= noCorrecto.toString()
             if (correcto==4){
                 gano.visibility= View.VISIBLE
                 retry()
                }
             else{ vectorImagenes=reiniciarVector(vectorImagenes) }
    } else { Toast.makeText(this, "Faltan colores", Toast.LENGTH_SHORT).show()} }

////botones de colores
botonRojo.setOnClickListener {
   if(i<4) {
      intento[i] = 0
      vectorImagenes[i].setImageResource(R.drawable.rojo)
      i++ }}
botonAmarillo.setOnClickListener {
   if(i<4) {
      intento[i] = 1
      vectorImagenes[i].setImageResource(R.drawable.amarllo)
      i++ }}
botonAzul.setOnClickListener {
   if(i<4) {
      intento[i] = 2
      vectorImagenes[i].setImageResource(R.drawable.azul)
      i++ }}
botonVerde.setOnClickListener {
   if(i<4) {
     intento[i] = 3
     vectorImagenes[i].setImageResource(R.drawable.verde)
     i++ }}


///boton reiniciar
    reiniciar.setOnClickListener {
    retry()
    if(gano.isVisible){gano.visibility= View.GONE }}

//boton eliminar
    borrar.setOnClickListener {
    if(i>0){
         i=i-1
         vectorImagenes[i].setImageResource(R.drawable.gris)
            }
        }
}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("i",i)
        outState.putInt("cant",cant)
        outState.putInt("correcto",correcto)
        outState.putInt("noCorrecto",noCorrecto)
        outState.putIntArray("codigoSecreto", codigoSecreto)
        outState.putIntArray("intento", intento)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        i=savedInstanceState.getInt("i")
        cant=savedInstanceState.getInt("cant")
        correcto=savedInstanceState.getInt("correcto")
        noCorrecto=savedInstanceState.getInt("noCorrecto")
        codigoSecreto= savedInstanceState.getIntArray("codigoSecreto")!!
        intento= savedInstanceState.getIntArray("intento")!!
        findViewById<TextView>(R.id.cantAcertados).text = correcto.toString()
        findViewById<TextView>(R.id.num).text = cant.toString()
        findViewById<TextView>(R.id.cantNoAcierto).text = noCorrecto.toString()
    }

}

