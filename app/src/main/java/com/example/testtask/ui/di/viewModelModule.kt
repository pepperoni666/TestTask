package com.example.testtask.ui.di

import com.example.testtask.model.DateRepository
import com.example.testtask.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get<DateRepository>())
    }
}