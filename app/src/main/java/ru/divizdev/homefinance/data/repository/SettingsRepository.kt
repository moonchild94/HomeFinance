package ru.divizdev.homefinance.data.repository

import io.reactivex.subjects.Subject
import ru.divizdev.homefinance.entities.Currency

interface SettingsRepository {
    fun subscribeMainCurrency(): Subject<Currency>
}