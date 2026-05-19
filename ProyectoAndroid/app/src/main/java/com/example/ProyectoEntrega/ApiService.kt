package com.example.ProyectoEntrega

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("juego/resultados/")
    suspend fun enviar(@Body resultados: ResultadosRequest): ResultadosResponse

}