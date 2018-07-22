package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import java.math.BigDecimal

class Converter {

    //Пока харкод, в будущем будем получать через конструктор
    private val mapConvert = mapOf(Pair(Currency.RUB,  mapOf(Pair(Currency.RUB, 1f),
                                    Pair(Currency.USD, 0.016f))),
                                   Pair(Currency.USD, mapOf(Pair(Currency.USD, 1f),
                                   Pair(Currency.RUB, 63.49f))))

    fun convert(moneyFrom: Money, currencyTo: Currency):Money {
        val map = mapConvert[moneyFrom.currency]
        val coef = map!![currencyTo]
        return Money(BigDecimal.valueOf(moneyFrom.value.toDouble() * coef!!), currencyTo)
    }

}
