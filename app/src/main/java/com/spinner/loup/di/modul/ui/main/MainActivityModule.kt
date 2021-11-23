package com.spinner.loup.di.modul.ui.main

import com.spinner.loup.domain.MainUseCase
import com.spinner.loup.ui.main.MainActivityPresenter
import com.spinner.loup.ui.main.MainActivityPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    @MainActivityScope
    fun provideMainActivityPresenter(mainUseCase: MainUseCase): MainActivityPresenter {
        return MainActivityPresenterImpl(mainUseCase)
    }
}