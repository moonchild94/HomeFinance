package ru.divizdev.homefinance.entities

import com.google.gson.annotations.SerializedName

data class CurrencyRateAPI(@SerializedName("USD_RUB") val rateRubUsd: Double)