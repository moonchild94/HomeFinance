package ru.divizdev.homefinance.entities

import java.math.BigDecimal
import java.math.RoundingMode

class Money(_value: BigDecimal, val currency: Currency)  {
    val value:BigDecimal = _value.setScale(2, RoundingMode.HALF_UP)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (currency != other.currency) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "Money(currency=$currency, value=$value)"
    }


}
