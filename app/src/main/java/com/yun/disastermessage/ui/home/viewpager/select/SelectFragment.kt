package com.yun.disastermessage.ui.home.viewpager.select

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.yun.disastermessage.R
import com.yun.disastermessage.BR
import com.yun.disastermessage.base.BaseBindingFragment
import com.yun.disastermessage.data.Constant
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.databinding.FragmentSelectBinding
import com.yun.disastermessage.ui.home.HomeViewModel
import com.yun.disastermessage.ui.popup.SpinnerPopup
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

        binding.apply {

            adView.loadAd(AdRequest.Builder().build())
            AdView(requireContext()).apply {
                adSize = AdSize.BANNER
            }

            mcvAddress.setOnClickListener {
                viewModel.locationNm.value = ""
                viewPagerFragment.callAddressApi()
            }

            btnSearch.setOnClickListener {
                if(viewModel.locationNm.value != ""){
                    viewPagerFragment.apply {
                        pageNo = 0
                        callMessageApi(true)
                    }
                } else{
                    Toast.makeText(requireContext(),"지역을 선택해 주세요",Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewPagerFragment.apply {
            locationList.observe(viewLifecycleOwner){
                if(it.size != 0 && viewModel.locationList.value != it){
                    viewModel.locationList.value = it
                    openDialog(viewModel.locationNm.value!! == "")
                }
            }

            screen.observe(viewLifecycleOwner){
                viewModel.screen.value = it
            }
        }

        viewModel.loading.observe(viewLifecycleOwner){
            viewPagerFragment.loading.value = it
        }

    }
    private fun openDialog(first: Boolean) {
        SpinnerPopup().apply {
            showPopup(
                requireContext(),
                viewModel.locationList.value!!
            )
            setDialogListener(object : SpinnerPopup.CustomDialogListener {
                override fun onResultClicked(position: Int) {
                    val name = viewModel.locationList.value!![position].name
                    val code = viewModel.locationList.value!![position].code
                    if(first){
                        viewPagerFragment.callAddressApi(code.substring(0,2)+Constant.SELECT_LOCATION)
                        viewModel.locationNm.value = name
                    } else{
                        viewModel.locationNm.value = name
                        viewPagerFragment.location.value = name
                    }
                }
            })
            viewModel.loading.value= false
        }
    }
}