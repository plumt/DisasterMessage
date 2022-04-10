package com.yun.disastermessage.di

import com.yun.disastermessage.data.repository.api.Api
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val apiModule = module {

    fun providerOpenApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
    fun providerAddressApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    single(named("open")) { providerOpenApi(get(named("open"))) }
    single(named("address")) { providerAddressApi(get(named("address"))) }
}