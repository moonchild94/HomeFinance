package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter

class WalletsPresenter(private val repositoryWallet: RepositoryWallet, parentPresenter: AbstractMainPresenter)
    : AbstractWalletsPresenter(parentPresenter) {

    private lateinit var wallets: List<Wallet>

    override fun onDeleteOperation(position: Int) {
        Completable.fromAction { repositoryWallet.delete(wallets[position]) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }

    override fun onEditOperation(position: Int, newName: String) {
        Completable.fromAction {
            wallets[position].walletName = newName
            repositoryWallet.update(wallets[position]) }
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