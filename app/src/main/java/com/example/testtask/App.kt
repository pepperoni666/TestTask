package com.example.testtask

import android.app.Application
import com.example.testtask.db.di.dbModule
import com.example.testtask.model.di.modelModule
import com.example.testtask.remote.di.remoteModule
import com.example.testtask.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(
                remoteModule,
                dbModule,
                modelModule,
                viewModelModule
            ))
        }
    }
}