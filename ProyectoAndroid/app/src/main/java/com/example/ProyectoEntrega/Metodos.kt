package com.example.ProyectoEntrega

import android.R
import android.widget.ImageView

fun correctos(secreto: IntArray, intento: IntArray, max: Int): IntArray{
        var correcto= 0
        var noCorrecto=0
        var vector= IntArray(2)
        var secretoB = BooleanArray(max)
        var secretoI = BooleanArray(max)
        for (j in 0..<max){
            if (secreto[j] == intento[j]){
                correcto++
                secretoB[j]=true
                secretoI[j]=true
            }else{
                secretoB[j]=false
                secretoI[j]=false
            }
        }
        for (j in 0..<max){
            if(secretoI[j]==false){
                for (n in 0..<max){
                    if(secretoB[n]==false && secreto[n]==intento[j]){
                        secretoB[n]=true
                        secretoI[j]=true
                        noCorrecto++
                        break
                    }
                }
            }
        }
        return intArrayOf(correcto, noCorrecto)
    }
fun reiniciarVector(vector: Array<ImageView>, max: Int): Array<ImageView> {
    for(j in 0..<max){
        vector[j].setImageResource(com.example.ProyectoEntrega.R.drawable.gris)
    }
    return vector;
}





