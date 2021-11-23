package com.spinner.loup.ui.main_two

enum class GroundGame {
    STONE_BG,
    SEA_BG
}

fun toGameBgStyle(GloveStyle: String?): GroundGame? {
    return try {
        if (GloveStyle != null) {
            GroundGame.valueOf(GloveStyle)
        } else {
            GroundGame.SEA_BG
        }
    } catch (ex: Exception) {
        GroundGame.STONE_BG
    }
}