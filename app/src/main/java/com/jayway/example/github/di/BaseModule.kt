package com.jayway.example.github.di

import com.jayway.example.github.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val baseModule: Module = applicationContext {
//    single("apiKey1") { "73e1f29766894a0fbb9b1dc4d1bc8061" } //jayway account
    viewModel { HomeViewModel() }
}

