package com.yun.disastermessage.di

import com.yun.disastermessage.util.PreferenceManager
import org.koin.dsl.module

val sharedPreferences = module {
    fun provideSharedPref(): PreferenceManager {
        return PreferenceManager
    }
    single { provideSharedPref() }
}