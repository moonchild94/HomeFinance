package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class DeleteWalletPresenter(private val repositoryWallet: RepositoryWallet) : AbstractDeleteWalletPresenter() {
    override fun onDeleteWallet(wallet: Wallet) {
        Completable.fromAction { repositoryWallet.delete(wallet) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }
}