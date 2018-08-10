package ru.divizdev.homefinance.data.repository

import android.content.SharedPreferences
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.presentation.settings.KEY_NAME_PREF

class SettingsRepositoryImpl(sharedPreferences: SharedPreferences) : SettingsRepository,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private val mainCurrencySubscription: Subject<Currency> = BehaviorSubject.create()

    init {
        mainCurrencySubscription.onNext(Currency.valueOf(sharedPreferences.getString(KEY_NAME_PREF, Currency.RUB.name)))
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun subscribeMainCurrency(): Subject<Currency> {
        return mainCurrencySubscription
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences, key: String) {
        if (key == KEY_NAME_PREF) {
            val newCurrency = Currency.valueOf(prefs.getString(key, Currency.RUB.name))
            mainCurrencySubscription.onNext(newCurrency)
        }
    }
}