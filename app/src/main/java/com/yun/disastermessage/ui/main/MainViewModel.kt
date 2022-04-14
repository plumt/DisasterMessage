package com.yun.disastermessage.ui.main

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.yun.disastermessage.R
import com.yun.disastermessage.base.BaseViewModel
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.Constant.ADS_RESET_CNT
import com.yun.disastermessage.data.Constant.SHAREDPREFERENCES_CNT_KEY
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.util.PreferenceManager

class MainViewModel(
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application) {

    val isLoading = MutableLiveData(false)

    var mInterstitialAd: InterstitialAd? = null

    lateinit var activity: Activity

    init {
        settingAds()
    }

    private fun settingAds(){

        InterstitialAd.load(mContext
        ,mContext.getString(R.string.admob_front_id)
        ,AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG,"onAdLoaded")
                mInterstitialAd = interstitialAd
                if(sharedPreferences.getInt(mContext, SHAREDPREFERENCES_CNT_KEY)!! >= Constant.ADS_SHOW_CNT){
                    Log.d(TAG,"111111")
                    showAds()
                }
                settCallBack()
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.d(TAG,"onAdFailedToLoad")
                mInterstitialAd = null
            }
        })






//        MobileAds.initialize(mContext){}
//        mInterstitialAd = InterstitialAd(this)
//
//        mInterstitialAd!!.adUnitId = mContext.getString(R.string.admob_front_test_id)
//        mInterstitialAd!!.loadAd(AdRequest.Builder().build())
//
//        mInterstitialAd!!.adListener = object : AdListener(){
//            override fun onAdClosed() {
//                Log.d(TAG,"광고 종료")
//                mInterstitialAd!!.loadAd(AdRequest.Builder().build())
//            }
//
//            override fun onAdLoaded() {
//                Log.d(TAG,"광고 준비 완료")
//                if(sharedPreferences.getInt(mContext, SHAREDPREFERENCES_CNT_KEY)!! >= ADS_SHOW_CNT){
//                    showAds()
//                }
//            }
//
//            override fun onAdOpened() {
//                sharedPreferences.setInt(mContext, SHAREDPREFERENCES_CNT_KEY, ADS_RESET_CNT)
//                Log.d(TAG,"광고 오픈")
//            }
//        }

    }

    fun settCallBack(){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
                settingAds()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
                settingAds()
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                sharedPreferences.setInt(mContext, SHAREDPREFERENCES_CNT_KEY, ADS_RESET_CNT)
                mInterstitialAd = null;
            }
        }
    }

    fun showAds(){
        Log.d(TAG,"showAds : $mInterstitialAd")
        mInterstitialAd?.show(activity)
    }
}