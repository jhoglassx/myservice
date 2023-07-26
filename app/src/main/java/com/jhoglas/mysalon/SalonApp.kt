package com.jhoglas.mysalon

import android.app.Application
import com.google.firebase.FirebaseApp

class SalonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
