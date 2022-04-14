package com.yun.disastermessage.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yun.disastermessage.R
import com.yun.disastermessage.data.model.AddressModel
import com.yun.disastermessage.databinding.DialogListBinding

class SpinnerPopup {
    lateinit var customDialogListener: CustomDialogListener

    fun showPopup(context: Context, item: ArrayList<AddressModel.Items>) {
        AlertDialog.Builder(context).run {
            setCancelable(false)
            val view = View.inflate(context, R.layout.dialog_list, null)
            val binding = DialogListBinding.bind(view)
//            binding.setVariable(BR.title, title)
            setView(binding.root)
            val dialog = create()
//            dialog.setOnDismissListener {
//                customDialogListener.onResultClicked(false)
//            }

            // 확인 버튼
//            view.findViewById<MaterialButton>(R.id.btn_result).setOnClickListener {
//                customDialogListener.onResultClicked(true)
//                dialog.dismiss()
//            }
            Log.d("lys", "item : $item")
            view.findViewById<RecyclerView>(R.id.rv_select).apply {
                adapter = object : RecyclerView.Adapter<RecyclerViewHolder>() {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ) = RecyclerViewHolder(parent)

                    override fun getItemCount(): Int = item.size

                    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
                        item[position].let { items ->
                            with(holder) {
                                title.text = items.name
                                card.setOnClickListener {
                                    customDialogListener.onResultClicked(items.id)
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }
            dialog
        }.show()
    }


    interface CustomDialogListener {
        fun onResultClicked(position: Int)
    }

    fun setDialogListener(customDialogListener: CustomDialogListener) {
        this.customDialogListener = customDialogListener
    }


}

class RecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_spinner, parent, false)
) {
    val title: TextView = itemView.findViewById<TextView>(R.id.tv_spinner_title)
    val card: CardView = itemView.findViewById<CardView>(R.id.card_view)
}

