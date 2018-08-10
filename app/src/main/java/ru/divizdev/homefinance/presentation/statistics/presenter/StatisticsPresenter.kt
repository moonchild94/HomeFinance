package ru.divizdev.homefinance.presentation.statistics.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.model.StatisticsInteractor
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import java.util.*

class StatisticsPresenter(private val repositoryWallet: RepositoryWallet,
                          private val statisticsInteractor: StatisticsInteractor,
                          parentPresenter: AbstractStatisticsMainPresenter) : AbstractStatisticsPresenter(parentPresenter) {
    private lateinit var wallets: List<Wallet>

    override fun loadWallets() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.showWallets(wallets.map { wallet -> wallet.walletName })
                }
    }

    override fun loadStatistics(categoryType: CategoryType, walletPosition: Int, dateFrom: Date, dateTo: Date) {
        statisticsInteractor.getSummaryByCategories(wallets[walletPosition], dateFrom, dateTo, categoryType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    weakReferenceView.get()?.showStatistics(it)
                }
    }
}