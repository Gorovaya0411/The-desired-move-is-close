package com.spinner.loup.di.component

import com.spinner.loup.di.modul.app.AppModule
import com.spinner.loup.di.modul.app.AppScope
import com.spinner.loup.di.modul.ui.main.MainActivityModule
import com.spinner.loup.di.modul.ui.main.MainActivitySubcomponent

import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun with(mainActivityModule: MainActivityModule): MainActivitySubcomponent
}