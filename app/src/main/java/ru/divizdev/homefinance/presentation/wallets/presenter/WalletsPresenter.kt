package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.data.FakeRepositoryWallet
import ru.divizdev.homefinance.data.IRepositoryWallet
import ru.divizdev.homefinance.entities.Wallet

class WalletsPresenter(val repositoryWallet: IRepositoryWallet = FakeRepositoryWallet()): AbstractWalletsPresenter() {
    override fun selectWallet(wallet: Wallet) {
        //Заготовка для выбора кошелька
    }

    override fun update() {
        super.update()
        val view = weakReferenceView.get()
        view?.setListWallets(repositoryWallet.getListWallet())
    }
}