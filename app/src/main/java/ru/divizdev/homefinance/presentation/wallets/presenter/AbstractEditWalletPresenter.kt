package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.wallets.ui.IEditWalletView

abstract class AbstractEditWalletPresenter : BaseMvpPresenter<IEditWalletView>() {

    abstract fun onEditWallet(wallet: Wallet, newName: String)
}