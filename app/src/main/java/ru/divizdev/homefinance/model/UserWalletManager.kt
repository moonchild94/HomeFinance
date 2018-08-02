package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.presentation.operation.view.OperationUI
import java.math.BigDecimal


class UserWalletManager(private val repositoryWallet: RepositoryWallet, private val converter: Converter) {
//В дальнейшем получать зависимости необходимо через Фабрику

    fun addOperation(keyWallet: Int, operationUI: OperationUI) {
//        val wallet = repositoryWallet.getWallet(keyWallet)
//        if (wallet == null) {
//            IllegalArgumentException("No wallet found")
//            return
//        }
//        val moneyCurrencyOperation = Money(BigDecimal.valueOf((operationUI.value
//                ?: 0f).toDouble()), operationUI.currency)
//        var moneyCurrencyMain = Money(BigDecimal.valueOf((operationUI.value
//                ?: 0f).toDouble()), operationUI.currency)
//        if (wallet.mainCurrency != moneyCurrencyOperation.currency) {
//            moneyCurrencyMain = converter.convert(moneyCurrencyOperation, wallet.mainCurrency)
//        }
//
//        val transaction = IdleOperation(operationUI.operationType, moneyCurrencyMain, moneyCurrencyOperation)
//
//        repositoryWallet.addOperation(keyWallet, transaction)
    }

    fun getBalance(currency: Currency): Money {
//        var allBalance: BigDecimal = BigDecimal.ZERO
//        val listWallet = repositoryWallet.getListWallet()
//        for (wallet in listWallet) {
//            var balance = wallet.getBalance()
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

    fun getBriefOverview(currency: Currency, operationType: OperationType): Money {
//        var allBalance: BigDecimal = BigDecimal.ZERO
//        val listWallet = repositoryWallet.getListWallet()
//        for (wallet in listWallet) {
//
//            var balance = when (operationType) {
//                OperationType.Expense -> wallet.getBalanceExpense()
//                OperationType.Revenue -> wallet.getBalanseRevenue()
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