package com.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cryptoapp.databinding.CryptoItemLayoutBinding
import com.cryptoapp.model.CoinMainData

class SelectedCryptoListAdapter(private val callback: (CoinMainData) -> Unit):
    ListAdapter<CoinMainData,SelectedCryptoListAdapter.CoinHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CryptoItemLayoutBinding.inflate(inflater,parent,false)
        return CoinHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinHolder, position: Int) {
        val coin = currentList[position]
        holder.binding.apply {
            Glide.with(root).load(coin.imageUrl).into(coinImage)
            coinNameAndCurrency.text = CoinMainData.getCoinNameAndCurrency(coin)
            coinPrice.text = coin.price
            lastUpdateTimeTextView.text = CoinMainData.getLastUpdateTime(coin.lastUpdate)
            root.setOnClickListener{
                callback.invoke(coin)
            }
        }
    }

    inner class CoinHolder(val binding: CryptoItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)
}

private val diffUtil = object: DiffUtil.ItemCallback<CoinMainData>(){
    override fun areItemsTheSame(oldItem: CoinMainData, newItem: CoinMainData): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinMainData, newItem: CoinMainData): Boolean {
        return oldItem == newItem
    }
}