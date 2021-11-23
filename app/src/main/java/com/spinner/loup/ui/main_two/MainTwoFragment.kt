package com.spinner.loup.ui.main_two

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.spinner.loup.R
import com.spinner.loup.databinding.FragmentMainTwoBinding
import com.spinner.loup.ui.base.BaseFragment
import com.spinner.loup.ui.main.MainActivity
import java.util.*

class MainTwoFragment : BaseFragment<FragmentMainTwoBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }
    private var minScreenWidth = 0f
    private var minScreenHeight = 0f
    private var score: Int = 0
    private var scoreReductionDuration = CHANGE_ACCOUNT
    private var durationFallRate = FALLING_SPEED
    private var isNewGame = false
    private var animPlate: ValueAnimator? = null
    private var objectsAndShark = ObjectsAndShark.values()
    private lateinit var handler: Handler
    private val looper = Looper.myLooper()
    private lateinit var runnableEndAnim: Runnable
    private val runnableStartAnim = Runnable {
        val objectGame = ImageView(contextActivity)
        val random = objectsAndShark.random().type
        binding.mainTwoScreenParent.addView(objectGame)
        objectGame.setImageResource(random)
        animateTranslationObjectGame(objectGame, random)
        setSizeObjectGame(objectGame)
        generateDolphin()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (looper != null) {
            handler = Handler(looper)
        }

        getScreenSize(contextActivity)
        generateDolphin()

        with(binding) {

            mainTwoScreenParent.setOnTouchListener(screenTouchActivation)
            mainTwoEndImg.visibility = ImageView.INVISIBLE
            score = contextActivity.presenter.getScore()
            durationFallRate = contextActivity.presenter.getDuration()
            mainTwoResultTxt.text = score.toString()
            mainTwoBestResultTxt.text = contextActivity.presenter.getMaxScore().toString()

            when (contextActivity.presenter.getGameBg()) {
                GroundGame.STONE_BG -> mainTwoScreenParent.setBackgroundResource(R.drawable.bg_main_two_stone)
                else -> mainTwoScreenParent.setBackgroundResource(R.drawable.bg_main)
            }

            mainTwoPauseImgBtn.setOnClickListener {
                contextActivity.soundCheckAndStartVibration()
                mainTwoScreenParent.removeAllViews()

                findNavController().navigate(R.id.action_mainTwoFragment_to_pauseFragment)
            }
        }
    }

    private val screenTouchActivation: View.OnTouchListener = object : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {

            with(binding) {
                val halfOfAScreen = minScreenWidth / 2

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val fingerPosition = event.x
                        if (fingerPosition < halfOfAScreen && mainTwoDolphinImg.x >= gameVerticalLeftGuideline.x) {
                            if (animPlate == null || !animPlate?.isStarted!!)
                                animPlate =
                                    animDolphinStart(
                                        mainTwoDolphinImg.x,
                                        gameVerticalLeftGuideline.x
                                    )
                        }

                        if (fingerPosition > halfOfAScreen && mainTwoDolphinImg.x <= gameVerticalRightGuideline.x) {
                            if (animPlate == null || !animPlate?.isStarted!!)
                                animPlate =
                                    animDolphinStart(
                                        mainTwoDolphinImg.x,
                                        gameVerticalRightGuideline.x
                                    )
                        }
                    }

                    MotionEvent.ACTION_UP ->
                        animPlate?.cancel()
                }
                return true
            }
        }
    }

    fun animDolphinStart(startX: Float, stopX: Float): ValueAnimator {

        with(binding) {
            val speed = 20f

            return ValueAnimator.ofFloat(
                mainTwoDolphinImg.translationY,
                mainTwoDolphinImg.translationY,
                startX,
                stopX
            ).apply {
                interpolator = LinearInterpolator()
                duration = 2000
                addUpdateListener {
                    var translationX = mainTwoDolphinImg.translationX
                    if (startX > stopX && mainTwoDolphinImg.x >= gameVerticalLeftGuideline.x) {
                        translationX -= speed
                        mainTwoDolphinImg.translationX = translationX
                    } else if (startX < stopX && mainTwoDolphinImg.x + mainTwoDolphinImg.width <= gameVerticalRightGuideline.x) {
                        translationX += speed
                        mainTwoDolphinImg.translationX = translationX
                    }
                }
                start()
            }
        }
    }

    private fun generateDolphin() {
        if (!isNewGame) {
            if (looper != null) {
                handler = Handler(looper)
            }
            handler.postDelayed(runnableStartAnim, DOLPHIN_GENERATE_DELAY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnableStartAnim)
    }

    private fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    private fun animateTranslationObjectGame(objectGame: ImageView, type: Int) {
        val coordinatesEndAnimation = (minScreenHeight * 0.85)
        val coordinatesDolphinAnimation = (minScreenHeight * 0.95)
        val random = Random()
        val white: Float =
            random.nextFloat() * (minScreenWidth - (objectGame.drawable.intrinsicWidth + 100))
        val animator = ObjectAnimator.ofFloat(
            objectGame,
            View.TRANSLATION_Y,
            binding.mainTwoScreenParent.top.toFloat() - distanceYStartAnimation,
            binding.mainTwoScreenParent.bottom.toFloat() + distanceYEndAnimation
        )

        with(animator) {
            if (score >= scoreReductionDuration) {
                if (score == scoreReductionDuration + SCORE_REDUCTION_DURATION) {
                    scoreReductionDuration += SCORE_REDUCTION_DURATION
                    durationFallRate -= (durationFallRate / 100 * REDUCTION_PERCENTAGE)
                    contextActivity.presenter.setDuration(durationFallRate)
                }
            }

            duration = durationFallRate.toLong()
            interpolator = LinearInterpolator()

            addUpdateListener {
                val progress = it.animatedValue as Float
                val height = objectGame.height
                val translationY = height + progress
                objectGame.translationY = translationY
                objectGame.translationX = white

                if (objectGame.translationY >= coordinatesEndAnimation || objectGame.translationY >= coordinatesDolphinAnimation) {
                    isOverlap(objectGame, type)
                }

                if (isNewGame) binding.mainTwoScreenParent.removeView(objectGame)
            }
            start()
        }
    }

    private fun isOverlap(objectGame: ImageView, type: Int) {

        with(binding) {
            if (type != ObjectsAndShark.SHARK.type) {
                val rectObjectGame = Rect()
                val rectDolf = Rect()
                val rectEndScreen = Rect()

                objectGame.getGlobalVisibleRect(rectObjectGame)
                mainTwoDolphinImg.getGlobalVisibleRect(rectDolf)
                mainTwoLowerThresholdView.getGlobalVisibleRect(rectEndScreen)

                if (rectObjectGame.intersect(rectDolf)) {
                    score += 20
                    mainTwoResultTxt.text = score.toString()
                    mainTwoScreenParent.removeView(objectGame)
                    contextActivity.presenter.setScore(score)
                    if (contextActivity.presenter.getMaxScore() < score) {
                        mainTwoBestResultTxt.text = score.toString()
                        contextActivity.presenter.setMaxScore(score)
                    }
                } else if (rectDolf.intersect(rectEndScreen)) {
                    score -= 10
                    if (score <= 0) score = 0
                    mainTwoResultTxt.text = score.toString()
                    mainTwoScreenParent.removeView(objectGame)
                }
            } else {
                val rectObjectGame = Rect()
                val rectDolphin = Rect()

                objectGame.getGlobalVisibleRect(rectObjectGame)
                mainTwoDolphinImg.getGlobalVisibleRect(rectDolphin)

                if (rectObjectGame.intersect(rectDolphin)) {
                    mainTwoScreenParent.removeAllViews()
                    score = 0
                    durationFallRate = 4000
                    mainTwoDolphinImg.visibility = ImageView.INVISIBLE
                    contextActivity.presenter.setScore(score)
                    contextActivity.presenter.setDuration(4000)
                    mainTwoResultTxt.text = score.toString()
                    handler.removeCallbacks(runnableStartAnim)
                    mainTwoEndImg.visibility = ImageView.VISIBLE

                    runnableEndAnim = Runnable {
                        mainTwoEndImg.visibility = ImageView.INVISIBLE
                        mainTwoDolphinImg.visibility = ImageView.VISIBLE
                        handler.post(runnableStartAnim)
                    }
                    handler.postDelayed(runnableEndAnim, 2000)
                }
            }
        }
    }

    private fun setSizeObjectGame(objectGame: ImageView) {
        val randomSizeObjectGame = (40..50).random().toDp(contextActivity)
        objectGame.layoutParams?.height = randomSizeObjectGame
        objectGame.layoutParams?.width = randomSizeObjectGame
    }

    private fun getScreenSize(activity: MainActivity) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
            windowMetrics.bounds.height() - insets.top - insets.bottom
            minScreenWidth = windowMetrics.bounds.width().toFloat()
            minScreenHeight = windowMetrics.bounds.height().toFloat()

        } else {
            val metrics = DisplayMetrics()
            contextActivity.windowManager.defaultDisplay.getMetrics(metrics)
            minScreenWidth = metrics.widthPixels.toFloat()
            minScreenHeight = metrics.heightPixels.toFloat()
        }
    }

    companion object {
        private const val SCORE_REDUCTION_DURATION = 200
        private const val REDUCTION_PERCENTAGE = 10
        private const val distanceYStartAnimation = 500
        private const val distanceYEndAnimation = 500
        private const val DOLPHIN_GENERATE_DELAY = 500L
        private const val FALLING_SPEED = 4000
        private const val CHANGE_ACCOUNT = 120
    }

    override fun initViewBinding() = FragmentMainTwoBinding.inflate(layoutInflater)
}