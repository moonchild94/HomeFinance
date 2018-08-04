package ru.divizdev.homefinance.model

import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.data.repository.RepositoryWalletOperation
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import java.math.BigDecimal

class OperationInteractor(private val repositoryWalletOperation: RepositoryWalletOperation,
                          private val repositoryWallet: RepositoryWallet,
                          private val converter: Converter) {

    fun addOperation(operation: Operation) {
        val mainCurrency = operation.wallet.balance.currency
        if (operation.sumCurrencyOperation.currency != mainCurrency) {
            operation.sumCurrencyMain = converter.convert(operation.sumCurrencyOperation, mainCurrency)
        }
        launch { repositoryWalletOperation.add(operation) }
    }

    fun getBalance(currency: Currency): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO
        val wallets = repositoryWallet.getAll()
        for (wallet in wallets) {
            var balance = wallet.balance
            balance = if (balance.currency == currency) {
                balance
            } else {
                converter.convert(balance, currency)
            }
            allBalance = allBalance.add(balance.value)
        }

        return Money(allBalance, currency)
    }

    fun getBriefOverview(currency: Currency, operationType: OperationType): Money {
//        var allBalance: BigDecimal = BigDecimal.ZERO
//        val listWallet = repositoryWallet.getListWallet()
//        for (wallet in listWallet) {
//
//            var balance = when (operationType) {
//                OperationType.OUTCOME -> wallet.getBalanceExpense()
//                OperationType.INCOME -> wallet.getBalanseRevenue()
//            }
//            balance = if (balance.currency == currency) {
//                balance
//            } else {
//                converter.convert(balance, currency)
//            }
//            allBalance = allBalance.add(balance.value)
//        }
//
//        return Money(allBalance, currency)
        return Money(BigDecimal.valueOf(0), currency)
    }
}