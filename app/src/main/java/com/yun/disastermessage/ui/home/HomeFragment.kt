package com.yun.disastermessage.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.base.BaseBindingFragment
import com.yun.disastermessage.data.Constant.LIST_SCREEN
import com.yun.disastermessage.data.Constant.SELECT_SCREEN
import com.yun.disastermessage.databinding.FragmentHomeBinding
import com.yun.disastermessage.ui.home.viewpager.list.ListFragment
import com.yun.disastermessage.ui.home.viewpager.select.SelectFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment
    : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class.java){
    override val viewModel: HomeViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vpMessage.run {
//                setPageTransformer(ZoomOutPageTransformer())
                isUserInputEnabled = false
                adapter = object : FragmentStateAdapter(this@HomeFragment){
                    override fun getItemCount(): Int = 2
                    override fun createFragment(position: Int): Fragment {
                        return when(position){
                            0 -> ListFragment()
                            1 -> SelectFragment()
                            else -> Fragment()
                        }
                    }
                }
            }
//            btnCall.setOnClickListener {
//                viewModel.pageNo = 0
//                viewModel.callMessageApi(true)
//            }

        }

        viewModel.apply {

//            location.observe(viewLifecycleOwner){
//                if(it != "") {
//                    callMessageApi(true)
//                }
//            }

            screen.observe(viewLifecycleOwner) {
                if (it == LIST_SCREEN || it == SELECT_SCREEN) {
                    binding.vpMessage.setCurrentItem(it, false)
                }
            }

            loading.observe(viewLifecycleOwner){
                sharedViewModel.isLoading.value = it
            }
        }



    }
}