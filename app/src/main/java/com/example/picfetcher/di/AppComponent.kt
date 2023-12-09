package com.example.picfetcher.di

import com.example.picfetcher.ui.PremierActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(activity: PremierActivity)
}