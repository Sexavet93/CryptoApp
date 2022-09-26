package com.cryptoapp

import android.app.Application
import com.cryptoapp.db.CoinRoom

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        CoinRoom.initializeRoom(applicationContext)
    }
}