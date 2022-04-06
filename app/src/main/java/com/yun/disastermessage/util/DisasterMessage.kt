package com.yun.disastermessage.util

import android.app.Application
import com.yun.disastermessage.di.apiModule
import com.yun.disastermessage.di.networkModule
import com.yun.disastermessage.di.sharedPreferences
import com.yun.disastermessage.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DisasterMessage : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DisasterMessage)
            koin.loadModules(
                listOf(
                    viewModelModule,
                    sharedPreferences,
                    networkModule,
                    apiModule
                )
            )
            koin.createRootScope()
        }
    }
}