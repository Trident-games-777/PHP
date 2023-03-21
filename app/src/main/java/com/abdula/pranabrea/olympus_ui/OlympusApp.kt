package com.abdula.pranabrea.olympus_ui

import android.app.Application
import android.content.Context
import com.abdula.pranabrea.olympus_di.mainModule
import com.abdula.pranabrea.olympus_di.olympusModule
import com.abdula.pranabrea.utils.OlympusConstance
import com.onesignal.OneSignal
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class OlympusApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@OlympusApp)
            modules(listOf(mainModule, olympusModule))
        }

        OneSignal.initWithContext(this)
        OneSignal.setAppId(OlympusConstance.ONE_SIGNAL_ID)
    }

    companion object {
        lateinit var appContext: Context
    }
}