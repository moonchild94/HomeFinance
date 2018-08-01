package ru.divizdev.homefinance

import android.app.Application
import ru.divizdev.homefinance.di.Factory

class ApplicationHomeFinance : Application() {


    override fun onCreate() {
        super.onCreate()
        Factory.create(this)
    }
}