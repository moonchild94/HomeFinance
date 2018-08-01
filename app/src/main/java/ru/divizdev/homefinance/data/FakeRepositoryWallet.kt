package ru.divizdev.homefinance.data

import ru.divizdev.homefinance.entities.*
import java.math.BigDecimal

class FakeRepositoryWallet : IRepositoryWallet {

    override fun addTransaction(key: Int, transaction: Transaction){
        val wallet = listWallet.get(key)
        wallet?.addTransaction(transaction)
    }

    private val listWallet: MutableMap<Int, Wallet> = mutableMapOf(
            0 to Wallet("Кошелек", Currency.RUB, mutableListOf(
                    Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10000.25), Currency.RUB)),
                    Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(20600), Currency.RUB)),
                    Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(35000), Currency.RUB)),
                    Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10250.25), Currency.RUB)),
                    Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(10000), Currency.RUB)),
                    Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(100), Currency.RUB)),
                    Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(560), Currency.RUB)),
                    Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(350), Currency.RUB)),
                    Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(25), Currency.RUB))
            )),
            1 to Wallet("Зарплатная карта", Currency.RUB, mutableListOf(Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(100.25), Currency.RUB)))),
            2 to Wallet("Накопительная карта", Currency.RUB, mutableListOf(Transaction(TypeTransaction.Expense, Money(BigDecimal.valueOf(10.25), Currency.RUB)))),
            3 to Wallet("Кредитная карта", Currency.RUB, mutableListOf(Transaction(TypeTransaction.Revenue, Money(BigDecimal.valueOf(32500.25), Currency.RUB))))
            )

    override fun getListWallet(): Collection<Wallet> = listWallet.values


    override fun getWallet(key: Int) = listWallet[key]

}