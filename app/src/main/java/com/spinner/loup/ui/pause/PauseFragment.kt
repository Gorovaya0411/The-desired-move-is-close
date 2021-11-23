package com.spinner.loup.ui.pause

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spinner.loup.R
import com.spinner.loup.databinding.FragmentPauseBinding
import com.spinner.loup.ui.base.BaseFragment
import com.spinner.loup.ui.main.MainActivity

class PauseFragment : BaseFragment<FragmentPauseBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pauseMenuBtn.setOnClickListener {
            contextActivity.soundCheckAndStartVibration()

            findNavController().navigate(R.id.action_pauseFragment_to_menuFragment)
        }

        binding.pauseContinueBtn.setOnClickListener {
            contextActivity.soundCheckAndStartVibration()

            findNavController().navigate(R.id.action_pauseFragment_to_mainTwoFragment)
        }

        binding.pauseNewGameBtn.setOnClickListener {
            contextActivity.soundCheckAndStartVibration()
            contextActivity.presenter.setScore(0)
            contextActivity.presenter.setDuration(4000)

            findNavController().navigate(R.id.action_pauseFragment_to_mainTwoFragment)
        }

        binding.pauseBestResultTxt.text = contextActivity.presenter.getScore().toString()
    }

    override fun initViewBinding() = FragmentPauseBinding.inflate(layoutInflater)
}