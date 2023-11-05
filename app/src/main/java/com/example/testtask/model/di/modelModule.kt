package com.example.testtask.model.di

import com.example.testtask.db.EmployeeDao
import com.example.testtask.model.DateRepository
import com.example.testtask.remote.RemoteService
import org.koin.dsl.module

val modelModule = module {
    factory { DateRepository(get<EmployeeDao>(), get<RemoteService>()) }
}