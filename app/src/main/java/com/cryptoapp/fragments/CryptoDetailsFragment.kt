package com.cryptoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cryptoapp.R
import com.cryptoapp.activities.MainActivity
import com.cryptoapp.adapters.SelectedCryptoListAdapter
import com.cryptoapp.databinding.FragmentCryptoDetailsBinding
import com.cryptoapp.model.CoinMainData

private const val COIN_KEY = "coin_key"

class CryptoDetailsFragment : Fragment() {

    private var _binding: FragmentCryptoDetailsBinding? = null
    private val binding: FragmentCryptoDetailsBinding
        get() = _binding ?: throw RuntimeException("CryptoDetailsFragment binding is null")
    private var coin: CoinMainData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coin = arguments?.getParcelable(COIN_KEY)
        (requireActivity() as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Coin Info"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCryptoDetailsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentContent()
    }

    private fun setFragmentContent(){
        coin?.let {
            binding.apply {
                Glide.with(root).load(it.imageUrl).into(coinImage)
                coinName.text = it.fromSymbol
                coinCurrency.text = it.toSymbol
                price.text = it.price
                minPrice.text = it.lowDay
                maxPrice.text = it.highDay
                lastMarket.text = it.lastMarket
                lastUpdate.text = CoinMainData.getLastUpdateTime(it.lastUpdate)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(coin: CoinMainData): CryptoDetailsFragment {
            return CryptoDetailsFragment().apply {
                arguments = Bundle().apply { putParcelable(COIN_KEY, coin) }
            }
        }
    }
}