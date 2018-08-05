package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class AddWalletPresenter(private val repositoryWallet: RepositoryWallet) : AbstractAddWalletPresenter() {
    override fun onAddWallet(wallet: Wallet) {
        Completable.fromAction { repositoryWallet.add(wallet) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }
}