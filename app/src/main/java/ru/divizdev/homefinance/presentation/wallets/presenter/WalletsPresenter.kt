package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class WalletsPresenter(private val repositoryWallet: RepositoryWallet) : AbstractWalletsPresenter() {
    private lateinit var wallets: List<Wallet>

    override fun onDeleteOperation(position: Int) {
        Completable.fromAction { repositoryWallet.delete(wallets[position]) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }

    override fun loadData() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.setListWallets(wallets)
                }
    }
}