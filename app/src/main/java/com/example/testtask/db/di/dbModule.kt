package com.example.testtask.db.di

import androidx.room.Room
import com.example.testtask.db.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDB::class.java, "database-name"
        ).build()
    }

    factory {
        get<AppDB>().employeeDao()
    }
}