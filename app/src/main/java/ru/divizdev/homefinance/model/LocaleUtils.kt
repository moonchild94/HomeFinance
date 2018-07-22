package ru.divizdev.homefinance.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by diviz on 10.02.2018.
 */

object LocaleUtils {

    private val RUSSIAN_LOCALE = Locale("RU")
    private val ENGLISH_LOCALE = Locale.ENGLISH

    val SYMBOL_PERCENT = "%"
    private var currentLocale: Locale? = null
        get() = if (field != null) {
            field
        } else RUSSIAN_LOCALE

    private val currentScale: Int
        get() = 2

    fun formatBigDecimal(decimal: BigDecimal): String {
        val decimalCopy = decimal.setScale(currentScale, RoundingMode.HALF_DOWN)
        val df = NumberFormat.getInstance(currentLocale) as DecimalFormat
        df.minimumFractionDigits = currentScale
        df.isGroupingUsed = true
        return df.format(decimalCopy)
    }

}


