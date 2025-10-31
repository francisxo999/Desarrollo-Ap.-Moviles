package com.example.vidasalud

import android.app.Application
import com.google.firebase.FirebaseApp

class VidaSaludApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}