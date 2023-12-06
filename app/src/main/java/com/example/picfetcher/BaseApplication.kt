package com.example.picfetcher

import android.app.Application
import com.example.picfetcher.di.AppComponent
import com.example.picfetcher.di.AppModule
import com.example.picfetcher.di.DaggerAppComponent
import com.example.picfetcher.di.NetworkModule


class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }

    fun getNetworkComponent(): AppComponent = appComponent

    companion object {
        lateinit var instance: BaseApplication private set
    }

}