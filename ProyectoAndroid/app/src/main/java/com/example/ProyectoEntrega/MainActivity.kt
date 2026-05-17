package com.example.ProyectoEntrega

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
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
    var max= 0

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
val botonRosa = findViewById<ImageButton>(R.id.rosa)
val botonAzul = findViewById<ImageButton>(R.id.azul)
val botonVerde = findViewById<ImageButton>(R.id.verde)
val botonAmarillo = findViewById<ImageButton>(R.id.amarillo)
val gano =findViewById<TextView>(R.id.gano)
val e= findViewById< ImageButton>(R.id.enviar)
val borrar= findViewById<ImageButton>(R.id.eliminar)
val reiniciar = findViewById<ImageButton>(R.id.reiniciar)
val ayuda= findViewById<ImageButton>(R.id.ayu)
//Creo vector de imagenes
var vectorImagenes = arrayOf(findViewById<ImageView>(R.id.btn0),
    findViewById<ImageView>(R.id.btn1),
    findViewById<ImageView>(R.id.btn2),
    findViewById<ImageView>(R.id.btn3),
    findViewById<ImageView>(R.id.btn4))
var vectorAnterior =arrayOf(findViewById<ImageView>(R.id.anteriorbtn0),
    findViewById<ImageView>(R.id.anteriorbtn1),
    findViewById<ImageView>(R.id.anteriorbtn2),
    findViewById<ImageView>(R.id.anteriorbtn3),
    findViewById<ImageView>(R.id.anteriorbtn4))
        //////////////////////////
val extras=intent.extras
val nivel=extras?.getInt("dificultad")
    if(nivel==0){
        max=3
        botonVerde.visibility= View.GONE
        botonRosa.visibility= View.GONE
        vectorAnterior[3].visibility= View.GONE
        vectorAnterior[4].visibility= View.GONE
        vectorImagenes[3].visibility= View.GONE
        vectorImagenes[4].visibility= View.GONE
        codigoSecreto= IntArray(3){ (0..2).random()
        }

        }else{
            if(nivel==1){
                max=4
                botonVerde.visibility= View.VISIBLE
                botonRosa.visibility= View.GONE
                vectorAnterior[3].visibility= View.VISIBLE
                vectorAnterior[4].visibility= View.GONE
                vectorImagenes[3].visibility= View.VISIBLE
                vectorImagenes[4].visibility= View.GONE
                codigoSecreto= IntArray(4){ (0..3).random() }
            }else{
                max=5
                botonVerde.visibility= View.VISIBLE
                botonRosa.visibility= View.VISIBLE
                vectorAnterior[3].visibility= View.VISIBLE
                vectorAnterior[4].visibility= View.VISIBLE
                vectorImagenes[3].visibility= View.VISIBLE
                vectorImagenes[4].visibility= View.VISIBLE
                codigoSecreto= IntArray(5){ (0..4).random() }
            }
    }
var intento=IntArray(max){(-1)}

//funcion para reiniciar
        fun retry () {
            cant = 1
            i = 0
            vectorImagenes=reiniciarVector(vectorImagenes, max)
            vectorAnterior=reiniciarVector(vectorAnterior, max)
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
    if(i==max){
         if(gano.isVisible){ gano.visibility= View.GONE}
             cant++
             i=0
             for (j in 0..<max) { vectorAnterior[j].setImageDrawable(vectorImagenes[j].drawable) }
             correcto=correctos(codigoSecreto,intento, max)[0]
             noCorrecto=correctos(codigoSecreto,intento, max)[1]
             findViewById<TextView>(R.id.num).text=cant.toString()
             findViewById<TextView>(R.id.cantAcertados).text= correcto.toString()
             findViewById<TextView>(R.id.cantNoAcierto).text= noCorrecto.toString()
             if (correcto==max){
                 gano.visibility= View.VISIBLE
                 retry()
                }
             else{ vectorImagenes=reiniciarVector(vectorImagenes,max) }
    } else { Toast.makeText(this, "Faltan colores", Toast.LENGTH_SHORT).show()} }

////botones de colores
botonRojo.setOnClickListener {
   if(i<max) {
      intento[i] = 0
      vectorImagenes[i].setImageResource(R.drawable.rojo)
      i++ }}
botonAmarillo.setOnClickListener {
   if(i<max) {
      intento[i] = 1
      vectorImagenes[i].setImageResource(R.drawable.amarllo)
      i++ }}
botonAzul.setOnClickListener {
   if(i<max) {
      intento[i] = 2
      vectorImagenes[i].setImageResource(R.drawable.azul)
      i++ }}
botonVerde.setOnClickListener {
   if(i<max) {
     intento[i] = 3
     vectorImagenes[i].setImageResource(R.drawable.verde)
     i++ }}
botonRosa.setOnClickListener {
   if(i<max) {
     intento[i] = 4
       vectorImagenes[i].setImageResource(R.drawable.rosa)
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
        //////////////////////

ayuda.setOnClickListener {
    val popup=PopupMenu(this, ayuda)
    popup.menuInflater.inflate(R.menu.menu_popup,popup.menu)
    popup.setOnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.opcion_ayuda -> { val i= Intent(this, Ayuda::class.java)
            startActivity(i)
            true }
            R.id.opcion_volver -> { finish(); true}
            else -> false
        }
    }
    popup.show()
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

