package com.example.picfetcher.di

import com.example.picfetcher.network.APIService
import com.example.picfetcher.ui.premierFragment.PicsListContract
import com.example.picfetcher.ui.premierFragment.PicsListFragment
import com.example.picfetcher.ui.premierFragment.PicsListPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)

    @Provides
    @Singleton
    fun providePresenter(apiService: APIService): PicsListContract.Presenter =
        PicsListPresenter(apiService)

    @Provides
    @Singleton
    fun providePicsListFragment(presenter: PicsListPresenter): PicsListFragment =
        PicsListFragment(presenter)

}