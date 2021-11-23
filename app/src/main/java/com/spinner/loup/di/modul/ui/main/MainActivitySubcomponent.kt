package com.spinner.loup.di.modul.ui.main

import com.spinner.loup.ui.main.MainActivityPresenter
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivitySubcomponent {
    val presenter: MainActivityPresenter
}