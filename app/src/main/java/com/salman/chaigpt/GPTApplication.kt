package com.salman.chaigpt

import android.app.Application
import com.salman.chaigpt.di.dataModule
import com.salman.chaigpt.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 */
class GPTApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GPTApplication)
            modules(
                dataModule,
                domainModule
            )
        }
    }
}