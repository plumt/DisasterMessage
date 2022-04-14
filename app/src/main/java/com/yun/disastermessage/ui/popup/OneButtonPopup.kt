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
import com.yun.disastermessage.databinding.DialogOneButtonBinding

class OneButtonPopup {
    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, title: String, contents: String){
        AlertDialog.Builder(context).run {
            setCancelable(false)
            val view = View.inflate(context, R.layout.dialog_one_button, null)
            val binding = DialogOneButtonBinding.bind(view)
            binding.setVariable(BR.title, title)
            binding.setVariable(BR.contents, contents)
            binding.setVariable(BR.btn_text, context.getString(R.string.result))
            setView(binding.root)
            val dialog = create()

            dialog.setOnDismissListener {
                customDialogListener.onResultClicked(true)
            }

            // 확인 버튼
            view.findViewById<MaterialButton>(R.id.btn_result).setOnClickListener {
                customDialogListener.onResultClicked(true)
                dialog.dismiss()
            }
            dialog
        }.show()
    }
    interface CustomDialogListener{
        fun onResultClicked(result: Boolean)
    }

    fun setDialogListener(customDialogListener: CustomDialogListener){
        this.customDialogListener = customDialogListener
    }
}