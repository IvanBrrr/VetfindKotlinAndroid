package com.egorov.vetfind

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.egorov.vetfind.util.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.MAPKIT_API_KEY)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}