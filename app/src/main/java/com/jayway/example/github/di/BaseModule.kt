package com.jayway.example.github.di

import com.jayway.example.github.data.service.GithubRepositories
import com.jayway.example.github.repositories.RepositoriesViewModel
import com.squareup.moshi.Moshi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val baseModule: Module = applicationContext {

    fun buildRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(get<HttpUrl>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()

//    single("apiKey1") { "73e1f29766894a0fbb9b1dc4d1bc8061" } //jayway account
    single { HttpUrl.parse("https://api.github.com/") as HttpUrl }

    single { Moshi.Builder().build() }
    single("headerInterceptor") {
        Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()

            chain.proceed(newRequest)
        }
    }
    single { OkHttpClient.Builder().addInterceptor(get("headerInterceptor")).build() }
    single { buildRetrofit() }
    single { get<Retrofit>().create(GithubRepositories::class.java)}
    viewModel { RepositoriesViewModel() }


}

