package ru.divizdev.homefinance.entities

import com.google.gson.annotations.SerializedName


//переделать загрузку на key-value, сейчас загрузка подходит только для одного курса
data class CurrencyRateAPI(@SerializedName("USD_RUB") val rateUsdRub: Float)