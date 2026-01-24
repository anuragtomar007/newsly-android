package com.anurag.newsly

import android.app.Application
import com.anurag.newsly.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewslyApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@NewslyApplication)
            modules(appModule)
        }
    }
}