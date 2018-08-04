package ru.divizdev.homefinance.presentation.wallets.presenter

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class WalletsPresenter(private val repositoryWallet: RepositoryWallet) : AbstractWalletsPresenter() {
    private lateinit var wallets: List<Wallet>

    override fun onDeleteOperation(position: Int) {
        launch { repositoryWallet.delete(wallets[position]) }
    }

    override fun loadData() {
        launch {
            wallets = repositoryWallet.getAll()
            launch(UI) { weakReferenceView.get()?.setListWallets(wallets) }
        }
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