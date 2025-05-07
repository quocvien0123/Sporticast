package com.sporticast

import android.app.Application

class App : Application() {

    // Singleton instance
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getContext() = applicationContext
}
