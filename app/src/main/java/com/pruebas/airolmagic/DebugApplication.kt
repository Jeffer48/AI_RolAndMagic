package com.pruebas.airolmagic

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

class DebugApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MI_PROPIA_APP", "¡¡¡DebugApplication.onCreate SÍ SE ESTÁ EJECUTANDO!!!")
        FirebaseApp.initializeApp(this)

        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
    }
}