package ru.divizdev.homefinance

import android.app.Application
import com.facebook.stetho.Stetho
import ru.divizdev.homefinance.di.Factory

class ApplicationHomeFinance : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Factory.create(this)
    }
}