package com.reelsjoke.app.core.extensions

import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.splashscreen.SplashScreen


/**
 * Created by bedirhansaricayir on 11.02.2024.
 */

fun SplashScreen.transitionAnimation() {
    this.setOnExitAnimationListener { splashScreenViewProvider ->
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, -1f,
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, 0f
        )
        translateAnimation.duration = 500L
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                splashScreenViewProvider.remove()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        splashScreenViewProvider.view.startAnimation(translateAnimation)
    }
}