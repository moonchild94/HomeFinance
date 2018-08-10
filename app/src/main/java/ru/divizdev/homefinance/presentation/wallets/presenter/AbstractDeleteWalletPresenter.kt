package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.wallets.ui.IDeleteWalletView

abstract class AbstractDeleteWalletPresenter : BaseMvpPresenter<IDeleteWalletView>() {

    abstract fun onDeleteWallet(wallet: Wallet)
}