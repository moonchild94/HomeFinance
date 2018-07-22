package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.FakeRepositoryWallet
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.TypeTransaction

class UserWalletManager {

    private val repositoryWallet = FakeRepositoryWallet()
    private val transactionManager = TransactionManager()

    fun getBalance(currency: Currency): Money {

        val balance = transactionManager.calculate(repositoryWallet.getWallet())

        return if (balance.currency == currency){
            balance
        }else{
            val converter = Converter()
            converter.convert(balance, currency)
        }
    }

    fun getBriefOverview(typeTransaction: TypeTransaction): Money{
        return transactionManager.calculate(repositoryWallet.getWallet(), typeTransaction)
    }


}