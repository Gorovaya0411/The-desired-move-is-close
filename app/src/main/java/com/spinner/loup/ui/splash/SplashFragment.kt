package com.spinner.loup.ui.splash

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spinner.loup.R
import com.spinner.loup.ui.main.MainActivity

class SplashFragment : Fragment() {
    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        contextActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}