package ru.divizdev.homefinance.presentation

import ru.divizdev.homefinance.entities.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by diviz on 10.02.2018.
 */

class LocaleUtils(private val currentLocale: Locale) {

    private val currentScale: Int
        get() = 2

    fun formatBigDecimal(decimal: BigDecimal): String {
        val decimalCopy = decimal.setScale(currentScale, RoundingMode.HALF_UP)
        val df = NumberFormat.getInstance(currentLocale) as DecimalFormat
        df.minimumFractionDigits = currentScale
        df.isGroupingUsed = true
        return df.format(decimalCopy)
    }

    fun formatCurrency(currency: Currency): String {
        return when (currency) {
            Currency.RUB -> "\u20BD"
            Currency.USD -> "\u0024"
        }
    }

}


