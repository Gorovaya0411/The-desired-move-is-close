package com.spinner.loup.ui.menu

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spinner.loup.R
import com.spinner.loup.databinding.FragmentMenuBinding
import com.spinner.loup.ui.base.BaseFragment
import com.spinner.loup.ui.main.MainActivity


class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuSwimImgBtn.setOnClickListener {
            contextActivity.soundCheckAndStartVibration()

            contextActivity.presenter.setScore(0)
            contextActivity.presenter.setDuration(4000)

            findNavController().navigate(R.id.action_menuFragment_to_mainTwoFragment)
        }

        binding.menuSettingsImgBtn.setOnClickListener {
            contextActivity.soundCheckAndStartVibration()

            findNavController().navigate(R.id.action_menuFragment_to_optionFragment)
        }

        binding.menuBestResultTxt.text = contextActivity.presenter.getMaxScore().toString()
    }

    override fun initViewBinding() = FragmentMenuBinding.inflate(layoutInflater)
}