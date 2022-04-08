package com.yun.disastermessage.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.button.MaterialButton
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.databinding.DialogTwoButtonBinding

class TwoButtonPopup {
    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, title: String, contents: String){
        AlertDialog.Builder(context).run {
            setCancelable(true)
            val view = View.inflate(context, R.layout.dialog_two_button, null)
            val binding = DialogTwoButtonBinding.bind(view)
            binding.setVariable(BR.title, title)
            binding.setVariable(BR.contents, contents)
            setView(binding.root)
            val dialog = create()

//            dialog.setOnDismissListener {
//                customDialogListener.onResultClicked(false)
//            }

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

    interface CustomDialogListener{
        fun onResultClicked(result: Boolean)
    }

    fun setDialogListener(customDialogListener: CustomDialogListener){
        this.customDialogListener = customDialogListener
    }
}