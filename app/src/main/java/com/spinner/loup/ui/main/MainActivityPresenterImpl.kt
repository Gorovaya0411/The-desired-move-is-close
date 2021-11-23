package com.spinner.loup.ui.main

import com.spinner.loup.domain.MainUseCase
import com.spinner.loup.ui.main_two.GroundGame
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainActivityPresenterImpl @Inject constructor(
    private val mainUseCase: MainUseCase

) : MainActivityPresenter() {


    override fun getCheckSound(): Boolean {
        return mainUseCase.checkSound
    }

    override fun setCheckSound(sound: Boolean) {
        mainUseCase.checkSound = sound
    }


    override fun getNumHive(): Int {
        return mainUseCase.checkNumHive
    }

    override fun setNumHive(numHive: Int) {
        mainUseCase.checkNumHive = numHive
    }

    override fun getQuantityOpenCard(): Int {
        return mainUseCase.quantityOpenCard
    }

    override fun setQuantityOpenCard(quantityOpenCard: Int) {
        mainUseCase.quantityOpenCard = quantityOpenCard
    }

    override fun getScore(): Int {
        return mainUseCase.score
    }

    override fun setScore(score: Int) {
        mainUseCase.score = score
    }

    override fun getMaxScore(): Int {
        return mainUseCase.maxScore
    }

    override fun setMaxScore(maxScore: Int) {
        mainUseCase.maxScore = maxScore
    }

    override fun setDuration(duration: Int) {
        mainUseCase.duration = duration
    }

    override fun getDuration(): Int {
       return mainUseCase.duration
    }

    override fun getGameBg(): GroundGame {
        return mainUseCase.checkGameBg
    }

    override fun setGameBg(bgGame: GroundGame) {
        mainUseCase.checkGameBg = bgGame
    }
}