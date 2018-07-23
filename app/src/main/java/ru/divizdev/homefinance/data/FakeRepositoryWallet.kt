package ru.divizdev.homefinance.data

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Transaction
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.entities.Wallet
import java.math.BigDecimal

class FakeRepositoryWallet: IRepositoryWallet{

    private val wallet = Wallet("Salary card", Currency.RUB, listOf(
            Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10000.25), Currency.RUB)),
            Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(20600), Currency.RUB)),
            Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(35000), Currency.RUB)),
            Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10250.25), Currency.RUB)),
            Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10000), Currency.RUB)),
            Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(100), Currency.RUB)),
            Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(560), Currency.RUB)),
            Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(350), Currency.RUB)),
            Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(25), Currency.RUB))


    ))

    override fun getWallet() = wallet

}