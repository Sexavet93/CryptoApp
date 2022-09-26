package com.cryptoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cryptoapp.model.CoinInfo

private const val DATABASE_NAME = "database"

@Database(entities = [CoinInfo::class], version = 1, exportSchema = false)
abstract class CoinRoom: RoomDatabase() {

    abstract fun getDao(): CoinDao

    companion object{
        @Volatile
        private var INSTANCE: CoinRoom? = null

        fun initializeRoom(context: Context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CoinRoom::class.java,
                    DATABASE_NAME).build()
            }
        }

        fun getInstance(): CoinRoom{
            synchronized(this){
                INSTANCE?.let { return it } ?: throw RuntimeException("Room instance is null")
            }
        }
    }
}