package com.example.ProyectoEntrega

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var cant = 1
    var codigoSecreto = IntArray(4) { (0..3).random() }
    var intento = IntArray(5) { (-1) }
    var correcto = 0
    var noCorrecto = 0
    var i = 0
    var max = 0

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets}

//CONSTANTES Y VARIABLES
    val botonRojo = findViewById<ImageButton>(R.id.rojo)
    val botonRosa = findViewById<ImageButton>(R.id.rosa)
    val botonAzul = findViewById<ImageButton>(R.id.azul)
    val botonVerde = findViewById<ImageButton>(R.id.verde)
    val botonAmarillo = findViewById<ImageButton>(R.id.amarillo)
    val e = findViewById<ImageButton>(R.id.enviar)
    val borrar = findViewById<ImageButton>(R.id.eliminar)
    val reiniciar = findViewById<ImageButton>(R.id.reiniciar)
    val ayuda = findViewById<ImageButton>(R.id.ayu)
    val tiempo = findViewById<TextView>(R.id.tiempo)
    val nombre = findViewById<TextView>(R.id.nombre)
    val valorAlto =99999999
    val pref = getSharedPreferences("RankingDB", Context.MODE_PRIVATE)
    val vectorpref = Array(5) { RegistroRanking("", valorAlto) }
    var t: Int
    val extras = intent.extras
    val nivel = extras?.getInt("dificultad")
    val n=extras?.getString("nombre")


//VECTORES DE IMAGENES, VECTOR PARA VER EL INTENTO ACTUAL Y VECTOR PARA VER EL INTENTO ANTERIOR.
    var vectorImagenes = arrayOf(
        findViewById<ImageView>(R.id.btn0),
        findViewById<ImageView>(R.id.btn1),
        findViewById<ImageView>(R.id.btn2),
        findViewById<ImageView>(R.id.btn3),
        findViewById<ImageView>(R.id.btn4))
    var vectorAnterior = arrayOf(
        findViewById<ImageView>(R.id.anteriorbtn0),
        findViewById<ImageView>(R.id.anteriorbtn1),
        findViewById<ImageView>(R.id.anteriorbtn2),
        findViewById<ImageView>(R.id.anteriorbtn3),
        findViewById<ImageView>(R.id.anteriorbtn4))



//////DIFICULTAD INGRESADA, 0= FACIL, 1= MEDIO, 2=DIFICIL.

    nombre.text = n.toString()
    if (nivel == 0) {
        max = 3
        t = 20

        botonVerde.visibility = View.GONE
        botonRosa.visibility = View.GONE
        vectorAnterior[3].visibility = View.GONE
        vectorAnterior[4].visibility = View.GONE
        vectorImagenes[3].visibility = View.GONE
        vectorImagenes[4].visibility = View.GONE
        codigoSecreto = IntArray(3) { (0..2).random() }}
    else {
        if (nivel == 1) {
            max = 4
            t = 25
            botonVerde.visibility = View.VISIBLE
            botonRosa.visibility = View.GONE
            vectorAnterior[3].visibility = View.VISIBLE
            vectorAnterior[4].visibility = View.GONE
            vectorImagenes[3].visibility = View.VISIBLE
            vectorImagenes[4].visibility = View.GONE
            codigoSecreto = IntArray(4) { (0..3).random() }}
        else {
            max = 5
            t = 30
            botonVerde.visibility = View.VISIBLE
            botonRosa.visibility = View.VISIBLE
            vectorAnterior[3].visibility = View.VISIBLE
            vectorAnterior[4].visibility = View.VISIBLE
            vectorImagenes[3].visibility = View.VISIBLE
            vectorImagenes[4].visibility = View.VISIBLE
            codigoSecreto = IntArray(5) { (0..4).random() } }
        }
        tiempo.text = "00:$t"






////FUNCION QUE ALMACENA SI ES NUEVO TOP
fun almacenar(nombre_jugador: String, puntaje: Int): Boolean {
    ////GUARDO EN EL VECTOR LO QUE ESTABA GUARDADO EN EL DISCO
    var b=false
    for (i in 0..4) {
        val nombreGuardado = pref.getString("nombre_$i", "") ?: ""
        val puntajeGuardado = pref.getInt("puntaje_$i", valorAlto)
        vectorpref[i] = RegistroRanking(nombreGuardado, puntajeGuardado)
    }


    ////COMPARO EL PUNTAJE QUE ME PASARON POR LA MOCHILA CON LOS PUNTAJES DEL TOP
    if (puntaje < vectorpref[4].puntaje) {
        vectorpref[4] = RegistroRanking(nombre_jugador, puntaje)
        b=true
        vectorpref.sortBy { it.puntaje }
        ///SI EL PUNTAJE ENTRA AL TOP, GUARDO EN EL DISCO EL NUEVO TOP
        pref.edit {
            for (i in 0..4) {
                putString("nombre_$i", vectorpref[i].nombre)
                putInt("puntaje_$i", vectorpref[i].puntaje)
            }
        }
    }
    return b;
}

////CREO CARTEL AL PERDER
    val vistaperdio= layoutInflater.inflate(R.layout.popupperder, null)
    val perder= AlertDialog.Builder(this)
    perder.setView(vistaperdio)
    perder.setCancelable(false)
    val cartelperder=perder.create()




////CREO CARTEL AL GANAR
    val vistagano=layoutInflater.inflate(R.layout.popupganar, null)
    val gano= AlertDialog.Builder(this)
    gano.setView(vistagano)
    gano.setCancelable(false)
    val cartelganar= gano.create()
    val ganorank=vistagano.findViewById<TextView>(R.id.rank)
    val ganonorank=vistagano.findViewById<TextView>(R.id.norank)
    val cantint= vistagano.findViewById<TextView>(R.id.inten)
    val compartir= vistagano.findViewById<TextView>(R.id.compartir)


///// INICIO EL TEMPORIZADOR SEGUN LA DIFICULTAD (T = TIEMPO X DIFICULTAD).

    var segs = t * 1000L
    val temporizador = object : CountDownTimer(segs, 1000) {
        override fun onTick(tiempoRestanteEnMilis: Long) {
            val segundosRestantes = tiempoRestanteEnMilis / 1000
            tiempo.text = "00:$segundosRestantes"}
        override fun onFinish() {
            vistaperdio.findViewById<TextView>(R.id.inten).text= "${getString(R.string.inte)} $cant"
            cartelperder.show()}
        }
//FUNCION PARA REINICIAR TOD0.
fun retry() {
    cant = 1
    i = 0
    vectorImagenes = reiniciarVector(vectorImagenes, max)
    vectorAnterior = reiniciarVector(vectorAnterior, max)
    findViewById<TextView>(R.id.cantAcertados).text = 0.toString()
    findViewById<TextView>(R.id.num).text = cant.toString()
    findViewById<TextView>(R.id.cantNoAcierto).text = 0.toString()
    codigoSecreto = IntArray(max) { (0..<max).random() }
    intento = IntArray(max) { (-1) }
    temporizador.cancel()
    tiempo.text = "00:$t"}

//CONFIGURO CARTEL AL PERDER
    vistaperdio.findViewById<TextView>(R.id.iniciopop).setOnClickListener { finish(); cartelperder.dismiss() }
    vistaperdio.findViewById<TextView>(R.id.reiniciarpop).setOnClickListener { retry(); cartelperder.dismiss() }


///// CONFIGURO CARTEL AL GANAR
    vistagano.findViewById<TextView>(R.id.iniciopop).setOnClickListener { finish(); cartelganar.dismiss()}
    vistagano.findViewById<TextView>(R.id.reiniciarpop).setOnClickListener { retry(); cartelganar.dismiss() }
    vistagano.findViewById<TextView>(R.id.aceptar).setOnClickListener {
        val i= Intent(this, PantallaRanking::class.java)
        startActivity(i)
        cartelganar.dismiss()
        finish()
    }
    compartir.setOnClickListener {
        val i= Intent(Intent.ACTION_SEND)
        i.type="text/plain"
        i.putExtra(Intent.EXTRA_TEXT,"${getString(R.string.adivine)} $cant ${getString(R.string.`in`)}")
        if(i.resolveActivity(packageManager) != null){startActivity(i)}
        cartelganar.dismiss()
        finish()
    }


//BOTON ENVIAR
e.setOnClickListener {
    correcto = 0
    noCorrecto = 0
    if (i == max) {
        if (cant == 1) {temporizador.start()}// SI ES EL PRIMER INTENTO REINICIA TEMPORIZADOR
        i = 0

        for (j in 0..<max) {vectorAnterior[j].setImageDrawable(vectorImagenes[j].drawable)}

        correcto = correctos(codigoSecreto, intento, max)[0]
        noCorrecto = correctos(codigoSecreto, intento, max)[1]
        findViewById<TextView>(R.id.cantAcertados).text = correcto.toString()
        findViewById<TextView>(R.id.cantNoAcierto).text = noCorrecto.toString()

        if (correcto == max) {
            temporizador.cancel()
            cantint.text= "${getString(R.string.inte)} $cant"
            if(almacenar(n.toString(), cant)){
                ganorank.visibility=View.VISIBLE
                compartir.visibility=View.VISIBLE
                ganonorank.visibility=View.GONE}
            else {
                ganorank.visibility=View.GONE
                compartir.visibility=View.GONE
                ganonorank.visibility=View.VISIBLE
            }
            cartelganar.show()}
        else
            {cant++
            findViewById<TextView>(R.id.num).text = cant.toString()
            vectorImagenes = reiniciarVector(vectorImagenes, max) }

        } else { Toast.makeText(this, R.string.noColores, Toast.LENGTH_SHORT).show() }}


////BOTONES DE COLORES

    botonRojo.setOnClickListener {
        if (i < max) {
            intento[i] = 0
            vectorImagenes[i].setImageResource(R.drawable.rojo)
            i++}}

    botonAmarillo.setOnClickListener {
        if (i < max) {
            intento[i] = 1
            vectorImagenes[i].setImageResource(R.drawable.amarllo)
            i++ }}

    botonAzul.setOnClickListener {
        if (i < max) {
            intento[i] = 2
            vectorImagenes[i].setImageResource(R.drawable.azul)
            i++}}

    botonVerde.setOnClickListener {
        if (i < max) {
            intento[i] = 3
            vectorImagenes[i].setImageResource(R.drawable.verde)
            i++ }}

    botonRosa.setOnClickListener {
        if (i < max) {
            intento[i] = 4
            vectorImagenes[i].setImageResource(R.drawable.rosa)
            i++ } }

///BOTON DE REINICIO DEL JUEGO.
    reiniciar.setOnClickListener {
        retry()}

//BOTON PARA ELIMINAR UN COLOR.
    borrar.setOnClickListener {
        if (i > 0) {
            i -= 1
            vectorImagenes[i].setImageResource(R.drawable.gris)}}

//////BOTON DE AYUDA PARA VOLVER AL LA PANTALLA PRINCIPAL O IR A PANTALLA AYUDA.

ayuda.setOnClickListener {
    val vista = layoutInflater.inflate(R.layout.popup, null)
    val pop = AlertDialog.Builder(this)
    pop.setView(vista)
    val mipopup = pop.create()
    vista.findViewById<TextView>(R.id.inicio).setOnClickListener {
        finish()
        temporizador.cancel()
        mipopup.dismiss() }
    vista.findViewById<TextView>(R.id.abrirayuda).setOnClickListener {
        val i=Intent(this, Ayuda::class.java)
        startActivity(i)
        mipopup.dismiss() }
    mipopup.show()}
}}
