package ru.divizdev.homefinance.presentation.wallets.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter

class WalletsPresenter(private val repositoryWallet: RepositoryWallet, parentPresenter: AbstractMainPresenter)
    : AbstractWalletsPresenter(parentPresenter) {

    private lateinit var wallets: List<Wallet>

    override fun loadData() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.setListWallets(wallets)
                }
    }

    override fun getWallet(position: Int): Wallet {
        return wallets[position]
    }
}