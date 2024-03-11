package com.reelsjoke.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

@HiltAndroidApp
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
