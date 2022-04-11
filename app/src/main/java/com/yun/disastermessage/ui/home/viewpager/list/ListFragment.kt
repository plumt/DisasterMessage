package com.yun.disastermessage.ui.home.viewpager.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.base.BaseBindingFragment
import com.yun.disastermessage.base.BaseRecyclerAdapter
import com.yun.disastermessage.data.Constant
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
            screen.observe(viewLifecycleOwner){
                viewModel.screen.value = it
            }
        }

        binding.apply {

            btnRetry.setOnClickListener {
                viewPagerFragment.screen.value = Constant.SELECT_SCREEN
            }

            rvMessage.run {
                adapter = object : BaseRecyclerAdapter.Create<MessageModel.RS.Row, ItemMessageBinding>(
                    R.layout.item_message,
                    bindingVariableId = BR.itemMessage,
                    bindingListener = BR.messageItemListener
                ){
                    override fun onItemClick(item: MessageModel.RS.Row, view: View) {

                    }
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollVertically(1) && !sharedViewModel.isLoading.value!!) {
                            viewPagerFragment.callMessageApi()
                        }

                        when(newState){
                            RecyclerView.SCROLL_STATE_DRAGGING ->binding.btnRetry.visibility = View.GONE
                            RecyclerView.SCROLL_STATE_IDLE -> binding.btnRetry.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }


    /*
    val onScrollListener = object : RecyclerView.OnScrollListener(){
                    var temp = 0
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollVertically(1) && !sharedViewModel.isLoading.value!!) {
                            viewPagerFragment.callMessageApi()
                        }
                        binding.btnRetry.visibility = View.VISIBLE
                        binding.vLineBottom.visibility = View.VISIBLE
                        temp = 1
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if(temp == 1){
                            super.onScrolled(recyclerView, dx, dy)
                            binding.btnRetry.visibility = View.GONE
                            binding.vLineBottom.visibility = View.GONE
                        }
                    }
                }
     */
}