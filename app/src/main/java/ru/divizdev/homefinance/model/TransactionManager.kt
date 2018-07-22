package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.TypeTransaction
import java.lang.IllegalArgumentException
import java.math.BigDecimal

class TransactionManager {

    fun calculate(wallet: Wallet, typeTransaction: TypeTransaction? = null): Money {
        var result = BigDecimal.ZERO
        val currency = wallet.mainCurrency
        for (transaction in wallet.listTransactions) {
            if (currency != transaction.sum.currency){
                IllegalArgumentException("All transactions must be in the same currency")
            }
            if (typeTransaction != null && typeTransaction != transaction.typeTransaction) {
             continue
            }
                result = if (transaction.typeTransaction == TypeTransaction.Revenue) {
                    result.add(transaction.sum.value)
                } else {
                    result.subtract(transaction.sum.value)
                }

        }

        return Money(result,  currency)
    }


}
