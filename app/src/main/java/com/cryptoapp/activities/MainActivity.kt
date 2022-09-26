package com.cryptoapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cryptoapp.R
import com.cryptoapp.databinding.ActivityMainBinding
import com.cryptoapp.fragments.CryptoListFragment
import com.cryptoapp.fragments.SelectedCryptsFragment
import com.cryptoapp.utils.OnTabSelectedListenerImpl
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        beginTransactionAnimationRightToLeft(SelectedCryptsFragment.newInstance())
        setTabLayoutListener()
    }

    private fun setTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListenerImpl() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    getString(R.string.my_crypts) -> {
                        if(supportFragmentManager.findFragmentById(R.id.fragmentContainer) !is SelectedCryptsFragment){
                            beginTransactionAnimationLeftToRight(SelectedCryptsFragment.newInstance())
                        }
                    }
                    getString(R.string.select_crypts) -> {
                        if(supportFragmentManager.findFragmentById(R.id.fragmentContainer) !is CryptoListFragment){
                            beginTransactionAnimationRightToLeft(CryptoListFragment.newInstance())
                        }
                    }
                }
            }
        })
    }

    fun beginTransactionAnimationRightToLeft(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    fun beginTransactionAnimationLeftToRight(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.slide_out,R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    fun selectTab(index: Int){
        binding.tabLayout.getTabAt(index)?.select()
    }

    override fun onSupportNavigateUp(): Boolean {
        beginTransactionAnimationLeftToRight(SelectedCryptsFragment.newInstance())
        return true
    }

}