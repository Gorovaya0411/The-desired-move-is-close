package com.spinner.loup.domain

import com.spinner.loup.data.service.SessionStoreService
import com.spinner.loup.ui.main_two.GroundGame
import javax.inject.Inject

interface MainUseCase {
    var checkSound: Boolean
    var checkNumHive: Int
    var quantityOpenCard: Int
    var score: Int
    var checkGameBg: GroundGame
    var duration: Int
    var maxScore: Int
}

class MainUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService,
) : MainUseCase {

    override var checkSound: Boolean
        get() = sessionStoreService.checkSound
        set(value) {
            sessionStoreService.checkSound = value
        }

    override var checkNumHive: Int
        get() = sessionStoreService.checkNumHive
        set(value) {
            sessionStoreService.checkNumHive = value
        }

    override var quantityOpenCard: Int
        get() = sessionStoreService.quantityOpenCard
        set(value) {
            sessionStoreService.quantityOpenCard = value
        }


    override var score: Int
        get() = sessionStoreService.score
        set(value) {
            sessionStoreService.score = value
        }

    override var checkGameBg: GroundGame
        get() = sessionStoreService.checkGameBg
        set(value) {
            sessionStoreService.checkGameBg = value
        }

    override var maxScore: Int
        get() = sessionStoreService.maxScore
        set(value) {
            sessionStoreService.maxScore = value
        }

    override var duration: Int
        get() = sessionStoreService.duration
        set(value) {
            sessionStoreService.duration = value
        }

}