package com.cryptoapp.api

import com.cryptoapp.model.CommonData
import com.cryptoapp.model.Raw
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_LIMIT) limit: Int = DEFAULT_COIN_AMOUNT,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Call<CommonData>

    @GET("pricemultifull")
    fun getFullCoinInfo(
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Call<Raw>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val DEFAULT_COIN_AMOUNT = 30
        private const val CURRENCY = "USD"
    }
}