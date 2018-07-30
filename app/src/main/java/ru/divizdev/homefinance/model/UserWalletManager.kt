package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.FakeRepositoryWallet
import ru.divizdev.homefinance.data.IRepositoryWallet
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Transaction
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.presentation.transaction.view.TransactionUI
import java.math.BigDecimal


class UserWalletManager(private val repositoryWallet: IRepositoryWallet = FakeRepositoryWallet(),
                        private val converter: Converter = Converter()) {
//В дальнейшем получать зависимости необходимо через Фабрику

    fun addTransaction(keyWallet: Int, transactionUI: TransactionUI) {
        val wallet = repositoryWallet.getWallet(keyWallet)
        if (wallet == null) {
            IllegalArgumentException("No wallet found")
            return
        }
        val moneyCurrencyOperation = Money(BigDecimal.valueOf((transactionUI.value
                ?: 0f).toDouble()), transactionUI.currency)
        var moneyCurrencyMain = Money(BigDecimal.valueOf((transactionUI.value
                ?: 0f).toDouble()), transactionUI.currency)
        if (wallet.mainCurrency != moneyCurrencyOperation.currency) {
            moneyCurrencyMain = converter.convert(moneyCurrencyOperation, wallet.mainCurrency)
        }

        val transaction = Transaction(transactionUI.typeTransaction, moneyCurrencyMain, moneyCurrencyOperation)

        repositoryWallet.addTransaction(keyWallet, transaction)

    }

    fun getBalance(currency: Currency): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO
        val listWallet = repositoryWallet.getListWallet()
        for (wallet in listWallet) {
            var balance = wallet.getBalance()
            balance = if (balance.currency == currency) {
                balance
            } else {
                converter.convert(balance, currency)
            }
            allBalance = allBalance.add(balance.value)
        }

        return Money(allBalance, currency)

    }

    fun getBriefOverview(currency: Currency, typeTransaction: TypeTransaction): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO
        val listWallet = repositoryWallet.getListWallet()
        for (wallet in listWallet) {

            var balance = when (typeTransaction) {
                TypeTransaction.Expense -> wallet.getBalanceExpense()
                TypeTransaction.Revenue -> wallet.getBalanseRevenue()
            }
            balance = if (balance.currency == currency) {
                balance
            } else {
                converter.convert(balance, currency)
            }
            allBalance = allBalance.add(balance.value)
        }

        return Money(allBalance, currency)
    }


}