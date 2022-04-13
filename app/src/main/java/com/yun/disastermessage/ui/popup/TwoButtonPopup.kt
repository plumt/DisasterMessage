package com.yun.disastermessage.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.button.MaterialButton
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.databinding.DialogTwoButtonBinding
import com.yun.disastermessage.util.Util.setAds

class TwoButtonPopup {
    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, title: String, contents: String){
        AlertDialog.Builder(context).run {
            setCancelable(true)
            val view = View.inflate(context, R.layout.dialog_two_button, null)
//            val template = view.findViewById<TemplateView>(R.id.my_template)
            val binding = DialogTwoButtonBinding.bind(view)
            binding.setVariable(BR.title, title)
            binding.setVariable(BR.contents, contents)
            setView(binding.root)
            val dialog = create()

//            dialog.setOnDismissListener {
//                customDialogListener.onResultClicked(false)
//            }

            setAds(context, view.findViewById(R.id.my_template))


            // 종료 버튼
            view.findViewById<MaterialButton>(R.id.btn_exit).setOnClickListener {
                customDialogListener.onResultClicked(true)
                dialog.dismiss()
            }
            // 취소 버튼
            view.findViewById<MaterialButton>(R.id.btn_cancel).setOnClickListener {
                customDialogListener.onResultClicked(false)
                dialog.dismiss()
            }
            dialog
        }.show()
    }

    private fun setAds(context: Context, templateView: TemplateView){
        val adLoader = AdLoader.Builder(context, context.getString(R.string.admob_native_test_id))
            .forNativeAd { ad: NativeAd ->
                // Show the ad.
                templateView.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build()
            )
            .build()
    }

    interface CustomDialogListener{
        fun onResultClicked(result: Boolean)
    }

    fun setDialogListener(customDialogListener: CustomDialogListener){
        this.customDialogListener = customDialogListener
    }
}