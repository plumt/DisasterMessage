package com.yun.disastermessage.ui.home.viewpager.select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.base.BaseBindingFragment
import com.yun.disastermessage.databinding.FragmentSelectBinding
import com.yun.disastermessage.ui.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SelectFragment
    : BaseBindingFragment<FragmentSelectBinding, SelectViewModel>(SelectViewModel::class.java){
    override val viewModel: SelectViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_select
    override fun initData(): Boolean = true
    override fun onBackEvent() { }
    override fun setVariable(): Int = BR.select

    val viewPagerFragment: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}