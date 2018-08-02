package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.repository.RepositoryCurrencyRate
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import java.math.BigDecimal

class Converter(val currencyRate: RepositoryCurrencyRate) {


    fun convert(moneyFrom: Money, currencyTo: Currency):Money {
        val mapConvert = currencyRate.getCurrentRate()
        val map = mapConvert[moneyFrom.currency]
        val coefficient = map?.get(currencyTo) ?: 0f
        return Money(BigDecimal.valueOf(moneyFrom.amount.toDouble() * coefficient), currencyTo)
    }

}
