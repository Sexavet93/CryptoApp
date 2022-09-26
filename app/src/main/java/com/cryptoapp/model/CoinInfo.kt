package com.cryptoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CoinInfo(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("Name")
    val name: String,
    @SerializedName("ImageUrl")
    var imageUrl: String
)
