package com.cryptoapp.model

import com.google.gson.annotations.SerializedName

data class CommonData(
    @SerializedName("Data")
    val coinList: List<Data>
)
