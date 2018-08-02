package ru.divizdev.homefinance.entities

import java.math.BigDecimal
import java.math.RoundingMode

class Money(amount: BigDecimal, var currency: Currency) {
    var amount: BigDecimal = amount
        set (value) {
            field = value.setScale(2, RoundingMode.HALF_UP)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (currency != other.currency) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }

    override fun toString(): String {
        return "Money(currency=$currency, amount=$amount)"
    }


}
