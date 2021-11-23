package com.spinner.loup.ui.main

import com.spinner.loup.ui.main_two.GroundGame
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainActivityView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun goToMenuFragment()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun goToOptionFragment()
}

abstract class MainActivityPresenter : MvpPresenter<MainActivityView>() {
    abstract fun getCheckSound(): Boolean
    abstract fun setCheckSound(sound: Boolean)
    abstract fun getNumHive(): Int
    abstract fun setNumHive(numHive: Int)
    abstract fun getQuantityOpenCard(): Int
    abstract fun setQuantityOpenCard(quantityOpenCard: Int)
    abstract fun getScore(): Int
    abstract fun setScore(score: Int)
    abstract fun getMaxScore(): Int
    abstract fun setMaxScore(maxScore: Int)
    abstract fun setDuration(duration: Int)
    abstract fun getDuration(): Int
    abstract fun getGameBg(): GroundGame
    abstract fun setGameBg(bgGame: GroundGame)
}