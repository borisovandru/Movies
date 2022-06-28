package com.borisov.movies.application

import android.app.Application
import com.borisov.movies.di.Di
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    Di.viewModelModule()
                )
            )
        }
    }
}