package com.cryptoapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://min-api.cryptocompare.com/data/")
        .build()

    val cryptoApi = retrofit.create(CryptoApi::class.java)
}