package com.spinner.loup.ui.option

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.spinner.loup.R
import com.spinner.loup.databinding.FragmentOptionBinding
import com.spinner.loup.ui.base.BaseFragment
import com.spinner.loup.ui.main.MainActivity
import com.spinner.loup.ui.main_two.GroundGame


class OptionFragment : BaseFragment<FragmentOptionBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            with(contextActivity) {

                optionBackImgBtn.setOnClickListener {
                    soundCheckAndStartVibration()
                    onBackPressed()
                }

                if (presenter.getCheckSound()) {
                    animForChoice(optionCheckSoundOnImgBtn, optionCheckSoundOffImgBtn)
                } else {
                    animForChoice(optionCheckSoundOffImgBtn, optionCheckSoundOnImgBtn)
                }

                when (presenter.getGameBg()) {
                    GroundGame.STONE_BG -> {
                        animForChoice(optionCheckStoneImgBtn, optionCheckSeaImgBtn)
                    }

                    GroundGame.SEA_BG -> {
                        animForChoice(optionCheckSeaImgBtn, optionCheckStoneImgBtn)
                    }
                }

                optionCheckSeaImgBtn.setOnClickListener {
                    soundCheckAndStartVibration()
                    animForChoice(optionCheckSeaImgBtn, optionCheckStoneImgBtn)
                    presenter.setGameBg(GroundGame.SEA_BG)
                }

                optionCheckStoneImgBtn.setOnClickListener {
                    soundCheckAndStartVibration()
                    animForChoice(optionCheckStoneImgBtn, optionCheckSeaImgBtn)
                    presenter.setGameBg(GroundGame.STONE_BG)
                }

                optionCheckSoundOnImgBtn.setOnClickListener {
                    soundCheckAndStartVibration()
                    animForChoice(optionCheckSoundOnImgBtn, optionCheckSoundOffImgBtn)
                    presenter.setCheckSound(true)
                }

                optionCheckSoundOffImgBtn.setOnClickListener {
                    soundCheckAndStartVibration()
                    animForChoice(optionCheckSoundOffImgBtn, optionCheckSoundOnImgBtn)
                    presenter.setCheckSound(false)
                }
            }
        }
    }

    private fun animForChoice(
        animObject: ImageButton,
        secondObject: ImageButton
    ) {
        animObject.setBackgroundResource(R.drawable.bg_option_anime_choice)
        secondObject.setBackgroundResource(R.drawable.ic_option_not_selected)
        (animObject.background as AnimationDrawable).also {
            it.isOneShot = true
        }.start()

    }

    override fun initViewBinding() = FragmentOptionBinding.inflate(layoutInflater)
}
