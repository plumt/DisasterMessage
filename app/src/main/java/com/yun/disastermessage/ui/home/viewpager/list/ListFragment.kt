package com.yun.disastermessage.ui.home.viewpager.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.base.BaseBindingFragment
import com.yun.disastermessage.base.BaseRecyclerAdapter
import com.yun.disastermessage.data.model.MessageModel
import com.yun.disastermessage.databinding.FragmentListBinding
import com.yun.disastermessage.databinding.ItemMessageBinding
import com.yun.disastermessage.ui.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment
    : BaseBindingFragment<FragmentListBinding, ListViewModel>(ListViewModel::class.java){
    override val viewModel: ListViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_list
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.list

    val viewPagerFragment: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerFragment.apply {
            messageItems.observe(viewLifecycleOwner) {
                viewModel.messageItems.value = it
            }
        }

        binding.apply {
            rvMessage.run {
                adapter = object : BaseRecyclerAdapter.Create<MessageModel.RS.Row, ItemMessageBinding>(
                    R.layout.item_message,
                    bindingVariableId = BR.itemMessage,
                    bindingListener = BR.messageItemListener
                ){
                    override fun onItemClick(item: MessageModel.RS.Row, view: View) {

                    }
                }
            }
        }

    }
}