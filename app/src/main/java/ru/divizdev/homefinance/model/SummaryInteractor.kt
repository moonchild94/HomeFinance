package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.OperationType
import java.math.BigDecimal

class SummaryInteractor(private val repositoryWallet: RepositoryWallet,
                        private val operationInteractor: OperationInteractor,
                        private val converter: Converter) {

    fun getBalance(currency: Currency): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO
        val wallets = repositoryWallet.getAll()
        for (wallet in wallets) {
            var balance = wallet.balance
            balance = if (balance.currency == currency) balance else converter.convert(balance, currency)
            allBalance = allBalance.add(balance.amount)
        }

        return Money(allBalance, currency)
    }

    fun getBriefOverview(currency: Currency, operationType: OperationType): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO
        val operations = operationInteractor.queryOperationsByType(operationType)

        for (operation in operations) {
            val balance = if (currency == operation.sumCurrencyOperation.currency) operation.sumCurrencyOperation
            else converter.convert(operation.sumCurrencyOperation, currency)
            allBalance = allBalance.add(balance.amount)
        }

        return Money(allBalance, currency)
    }
}