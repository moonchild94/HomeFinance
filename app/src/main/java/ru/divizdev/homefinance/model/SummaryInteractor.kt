package ru.divizdev.homefinance.model

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.data.repository.SettingsRepository
import ru.divizdev.homefinance.entities.*
import java.math.BigDecimal

class SummaryInteractor(repositoryWallet: RepositoryWallet,
                        repositoryOperation: RepositoryOperation,
                        settingsRepository: SettingsRepository,
                        private val converter: Converter) {

    val balanceMain: Subject<Money> = BehaviorSubject.create()
    val balanceSecondary: Subject<Money> = BehaviorSubject.create()

    val briefOverviewIncome: Subject<Money> = BehaviorSubject.create()
    val briefOverviewOutcome: Subject<Money> = BehaviorSubject.create()

    init {

        settingsRepository.subscribeMainCurrency()
                .observeOn(Schedulers.io())
                .subscribe {

                    val mainCurrency = it
                    val secondaryCurrency = Currency.values().find { it != mainCurrency }
                            ?: Currency.USD
                    repositoryWallet.getAll()
                            .observeOn(Schedulers.io())
                            .subscribe {
                                balanceMain.onNext(updateBalance(it, mainCurrency))
                                balanceSecondary.onNext(updateBalance(it, secondaryCurrency))
                            }

                    repositoryOperation.queryByType(CategoryType.INCOME)
                            .observeOn(Schedulers.io())
                            .subscribe {
                                briefOverviewIncome.onNext(updateBriefOverview(it, mainCurrency))
                            }

                    repositoryOperation.queryByType(CategoryType.OUTCOME)
                            .observeOn(Schedulers.io())
                            .subscribe {
                                briefOverviewOutcome.onNext(updateBriefOverview(it, mainCurrency))
                            }
                }
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