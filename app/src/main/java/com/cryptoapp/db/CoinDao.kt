package com.cryptoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cryptoapp.model.CoinInfo

@Dao
interface CoinDao {

    @Query("SELECT * FROM coininfo")
    fun getSelectedCoins(): LiveData<List<CoinInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedCoin(selectedCoin: CoinInfo)

    @Delete
    suspend fun deleteSelectedCoin(selectedCoin: CoinInfo)
}