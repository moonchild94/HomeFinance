package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.wallets.ui.IWalletsView

abstract class AbstractWalletsPresenter : BaseMvpPresenter<IWalletsView>() {
    abstract fun selectWallet(wallet: Wallet)
}