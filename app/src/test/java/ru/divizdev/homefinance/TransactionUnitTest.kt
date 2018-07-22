package ru.divizdev.homefinance

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Transaction
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.model.TransactionManager
import ru.divizdev.homefinance.model.Wallet
import java.math.BigDecimal

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TransactionUnitTest {
    @Test
    fun expenseTransactions() {

        val transactionFirst = Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(100.405), Currency.USD))
        val transactionSecond = Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val wallet = Wallet(Currency.USD,  listOf(transactionFirst, transactionSecond))
        val transactionManager = TransactionManager()

        val money = transactionManager.calculate(wallet)

        assertEquals(BigDecimal.valueOf(-125.51).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }

    @Test
    fun profitableTransactions() {
        val transactionFirst = Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(100.40), Currency.USD))
        val transactionSecond = Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val transactionThird = Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(35), Currency.USD))
        val wallet = Wallet(Currency.USD,  listOf(transactionFirst, transactionSecond, transactionThird))

        val transactionManager = TransactionManager()

        val money = transactionManager.calculate(wallet)

        assertEquals(BigDecimal.valueOf(160.50).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }

    @Test
    fun otherTransactions() {
        val transactionFirst = Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(100.40), Currency.USD))
        val transactionSecond = Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(25.10), Currency.USD))
        val transactionThird = Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(35), Currency.USD))
        val wallet = Wallet(Currency.USD,  listOf(transactionFirst, transactionSecond, transactionThird))
        val transactionManager = TransactionManager()

        val money = transactionManager.calculate(wallet)

        assertEquals(BigDecimal.valueOf(110.30).setScale(2), money.value)
        assertEquals(Currency.USD, money.currency)
    }


}
