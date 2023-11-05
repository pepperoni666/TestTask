package com.example.testtask.remote.di

import com.example.testtask.remote.RemoteService
import org.koin.dsl.module

val remoteModule = module {
    single { RemoteService() }
}