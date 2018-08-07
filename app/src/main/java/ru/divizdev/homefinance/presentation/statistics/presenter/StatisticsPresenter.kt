package ru.divizdev.homefinance.presentation.statistics.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
import java.util.*

class StatisticsPresenter(private val repositoryOperation: RepositoryOperation,
                          private val repositoryWallet: RepositoryWallet) : AbstractStatisticsPresenter() {
    private lateinit var wallets: List<Wallet>

    override fun loadWallets() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.showWallets(wallets.map { wallet -> wallet.walletName })
                }
    }

    override fun loadStatistics(operationType: OperationType, walletPosition: Int, dateFrom: Date, dateTo: Date) {
        repositoryOperation.getSummaryByCategories(wallets[walletPosition], dateFrom, dateTo, operationType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    weakReferenceView.get()?.showStatistics(it)
                }
    }
}