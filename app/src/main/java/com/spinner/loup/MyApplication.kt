package com.spinner.loup

import android.app.Application
import com.spinner.loup.di.component.AppComponent
import com.spinner.loup.di.modul.app.AppModule

import com.spinner.loup.di.component.DaggerAppComponent



class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}