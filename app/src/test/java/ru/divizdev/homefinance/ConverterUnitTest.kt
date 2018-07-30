package ru.divizdev.homefinance

import org.junit.Assert
import org.junit.Test
import ru.divizdev.homefinance.data.RepositoryCurrencyRate
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.model.Converter
import java.math.BigDecimal


/**
 * Tests for [Converter]
 */
class ConverterUnitTest {

    private val converter = Converter(RepositoryCurrencyRate())
    @Test
    fun convertMoneyRubToUsd() {
        val moneyRub = Money(BigDecimal.valueOf(70), Currency.RUB)

        val moneyUsd = converter.convert(moneyRub, Currency.USD)

        Assert.assertEquals(BigDecimal.valueOf(1.12), moneyUsd.value)
        Assert.assertEquals(Currency.USD, moneyUsd.currency)

    }

    @Test
    fun convertMoneyRubToRub() {
        val moneyRub = Money(BigDecimal.valueOf(10), Currency.RUB)

        val moneyTo = converter.convert(moneyRub, Currency.RUB)

        Assert.assertEquals(BigDecimal.valueOf(10).setScale(2), moneyTo.value)
        Assert.assertEquals(Currency.RUB, moneyTo.currency)

    }

    @Test
    fun convertMoneyUsdToRub() {
        val moneyUsd = Money(BigDecimal.valueOf(20), Currency.USD)

        val moneyTo = converter.convert(moneyUsd, Currency.RUB)

        Assert.assertEquals(BigDecimal.valueOf(1269.8).setScale(2), moneyTo.value)
        Assert.assertEquals(Currency.USD, moneyUsd.currency)

    }

    @Test
    fun convertMoneyUsdToUsd() {
        val moneyRub = Money(BigDecimal.valueOf(50), Currency.USD)

        val moneyUsd = converter.convert(moneyRub, Currency.USD)

        Assert.assertEquals(BigDecimal.valueOf(50).setScale(2), moneyUsd.value)
        Assert.assertEquals(Currency.USD, moneyUsd.currency)

    }
}