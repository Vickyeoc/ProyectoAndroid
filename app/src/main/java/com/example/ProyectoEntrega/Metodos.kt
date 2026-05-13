package com.example.ProyectoEntrega

import android.R
import android.widget.ImageView

fun correctos(secreto: IntArray, intento: IntArray): IntArray{
        var correcto= 0
        var noCorrecto=0
        var vector= IntArray(2)
        var secretoB = BooleanArray(4)
        var secretoI = BooleanArray(4)
        for (j in 0..3){
            if (secreto[j] == intento[j]){
                correcto++
                secretoB[j]=true
                secretoI[j]=true
            }else{
                secretoB[j]=false
                secretoI[j]=false
            }
        }
        for (j in 0..3){
            if(secretoI[j]==false){
                for (n in 0..3){
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
fun reiniciarVector(vector: Array<ImageView>): Array<ImageView> {
    for(j in 0..3){
        vector[j].setImageResource(com.example.ProyectoEntrega.R.drawable.gris)
    }
    return vector;
}





