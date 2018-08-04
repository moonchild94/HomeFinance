package ru.divizdev.homefinance.presentation.wallets.presenter

import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class AddWalletPresenter(private val repositoryWallet: RepositoryWallet) : AbstractAddWalletPresenter() {
    override fun onAddWallet(wallet: Wallet) {
        launch { repositoryWallet.add(wallet) }
    }
}