package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.wallets.ui.IAddWalletView

abstract class AbstractAddWalletPresenter : BaseMvpPresenter<IAddWalletView>() {

    abstract fun onAddWallet(wallet: Wallet)
}