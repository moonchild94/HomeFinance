package ru.divizdev.homefinance

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.divizdev.homefinance.entities.*
import java.math.BigDecimal

/**
 * Tests for [Transaction]
 */
class OperationUnitTest {
    @Test
    fun expenseTransactions() {

        val transactionFirst = Transaction(OperationType.Expense, Money(BigDecimal.valueOf(100.405), Currency.USD))
        val transactionSecond = Transaction(OperationType.Expense, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val wallet = Wallet("Sberbank", Currency.USD, mutableListOf(transactionFirst, transactionSecond))


        val money = wallet.getBalance()

        assertEquals(BigDecimal.valueOf(-125.51).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }

    @Test
    fun profitableTransactions() {
        val transactionFirst = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(100.40), Currency.USD))
        val transactionSecond = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val transactionThird = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(35), Currency.USD))
        val wallet = Wallet("Sberbank", Currency.USD, mutableListOf(transactionFirst, transactionSecond, transactionThird))


        val money = wallet.getBalance()

        assertEquals(BigDecimal.valueOf(160.50).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }

    @Test
    fun otherTransactions() {
        val transactionFirst = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(100.40), Currency.USD))
        val transactionSecond = Transaction(OperationType.Expense, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val transactionThird = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(35), Currency.USD))
        val wallet = Wallet("Sberbank", Currency.USD, mutableListOf(transactionFirst, transactionSecond, transactionThird))


        val money = wallet.getBalance()

        assertEquals(BigDecimal.valueOf(110.30).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }

    @Test
    fun filterTransactions() {
        val transactionFirst = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(100.40), Currency.USD))
        val transactionSecond = Transaction(OperationType.Expense, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val transactionThird = Transaction(OperationType.Revenue, Money(BigDecimal.valueOf(35), Currency.USD))
        val wallet = Wallet("Sberbank", Currency.USD, mutableListOf(transactionFirst, transactionSecond, transactionThird))


        val moneyRevenue = wallet.getBalanseRevenue()
        val moneyExpense = wallet.getBalanceExpense()

        assertEquals(BigDecimal.valueOf(-25.10).setScale(2), moneyExpense.value)
        assertEquals(BigDecimal.valueOf(135.40).setScale(2), moneyRevenue.value)

    }


}
