package com.yun.disastermessage.di

import android.widget.ListView
import com.yun.disastermessage.ui.home.HomeViewModel
import com.yun.disastermessage.ui.home.viewpager.list.ListViewModel
import com.yun.disastermessage.ui.home.viewpager.select.SelectViewModel
import com.yun.disastermessage.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(get(),get())
    }

    viewModel {
        HomeViewModel(get(), get(), get())
    }

    viewModel {
        ListViewModel(get())
    }

    viewModel {
        SelectViewModel(get())
    }
}