package ru.divizdev.homefinance.entities

import java.lang.IllegalArgumentException
import java.math.BigDecimal

//Предполагается что в кошельке все транзакции с одной главной валютой. При добавлении транзакции будет осуществляться конвертация в валюту кошелька
class Wallet(val name: String, val mainCurrency: Currency, var listTransactions: MutableList<Transaction>) {
    fun getBalance(): Money {
        return calculate()
    }

    fun addTransaction(transaction: Transaction){
        if(transaction.sumCurrencyMain.currency != mainCurrency){
            IllegalArgumentException("All transactions must be in the main currency")
        }
        listTransactions.add(transaction)
    }

    fun getBalanceExpense(): Money{
        return calculate(TypeTransaction.Expense)
    }

    fun getBalanseRevenue(): Money{
        return calculate(TypeTransaction.Revenue)
    }

    private fun calculate(filterTypeTransaction: TypeTransaction? = null): Money {
        var result = BigDecimal.ZERO

        for (transaction in listTransactions) {
            if (mainCurrency != transaction.sumCurrencyMain.currency) {
                IllegalArgumentException("All transactions must be in the same currency")
            }
            if (filterTypeTransaction != null && filterTypeTransaction != transaction.typeTransaction) {
                continue
            }
            result = if (transaction.typeTransaction == TypeTransaction.Revenue) {
                result.add(transaction.sumCurrencyMain.value)
            } else {
                result.subtract(transaction.sumCurrencyMain.value)
            }

        }

        return Money(result, mainCurrency)
    }
}

fun emptyWallet(): Wallet {
    return Wallet("", Currency.USD, mutableListOf())
}
