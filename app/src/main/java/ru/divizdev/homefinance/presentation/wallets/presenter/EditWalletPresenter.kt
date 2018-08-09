package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class EditWalletPresenter(private val repositoryWallet: RepositoryWallet) : AbstractEditWalletPresenter() {
    override fun onEditWallet(wallet: Wallet, newName: String) {
        Completable.fromAction {
            wallet.walletName = newName
            repositoryWallet.update(wallet) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }
}