package ru.divizdev.homefinance.presentation.wallets.presenter

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class WalletsPresenter(private val repositoryWallet: RepositoryWallet) : AbstractWalletsPresenter() {
    override fun loadData() {
        launch {
            val wallets = repositoryWallet.getAll()
            launch(UI) { weakReferenceView.get()?.setListWallets(wallets) }
        }
    }

    override fun selectWallet(wallet: Wallet) {
        //Заготовка для выбора кошелька
    }

    override fun update() {
        super.update()
        val view = weakReferenceView.get()
        launch {
            val wallets = repositoryWallet.getAll()
            launch(UI) { view?.setListWallets(wallets) }
        }
    }
}