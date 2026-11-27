package com.duoc.albalab.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IndicadoresInstance {

    val api: IndicadoresApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://mindicador.cl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IndicadoresApi::class.java)
    }
}