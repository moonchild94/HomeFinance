package ru.divizdev.homefinance.model

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.*
import java.math.BigDecimal

class SummaryInteractor(repositoryWallet: RepositoryWallet,
                        repositoryOperation: RepositoryOperation,
                        private val converter: Converter) {

    val balanceRUB: Subject<Money> = BehaviorSubject.create()
    val balanceUSD: Subject<Money> = BehaviorSubject.create()

    val briefOverviewIncome: Subject<Money> = BehaviorSubject.create()
    val briefOverviewOutcome: Subject<Money> = BehaviorSubject.create()

    init {
        Completable.fromAction {
            repositoryWallet.getAll()
                    .observeOn(Schedulers.io())
                    .subscribe {
                        balanceRUB.onNext(updateBalance(it, Currency.RUB))
                        balanceUSD.onNext(updateBalance(it, Currency.USD))
                    }

            repositoryOperation.queryByType(CategoryType.INCOME)
                    .observeOn(Schedulers.io())
                    .subscribe {
                        briefOverviewIncome.onNext(updateBriefOverview(it, Currency.RUB))
                    }

            repositoryOperation.queryByType(CategoryType.OUTCOME)
                    .observeOn(Schedulers.io())
                    .subscribe {
                        briefOverviewOutcome.onNext(updateBriefOverview(it, Currency.RUB))
                    }
        }.subscribeOn(Schedulers.io()).subscribe {}
    }

    private fun updateBriefOverview(operations: List<Operation>, currency: Currency): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO

        for (operation in operations) {
            val balance = if (currency == operation.sumCurrencyOperation.currency) operation.sumCurrencyOperation
            else converter.convert(operation.sumCurrencyOperation, currency)
            allBalance = allBalance.add(balance.amount)
        }

        return Money(allBalance, currency)
    }

    private fun updateBalance(wallets: List<Wallet>, currency: Currency): Money {
        var allBalance: BigDecimal = BigDecimal.ZERO

        for (wallet in wallets) {
            var balance = wallet.balance
            balance = if (balance.currency == currency) balance else converter.convert(balance, currency)
            allBalance = allBalance.add(balance.amount)
        }

        return Money(allBalance, currency)
    }
}