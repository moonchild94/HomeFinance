package ru.divizdev.homefinance.presentation.wallets.ui

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.IMvpView

interface IWalletsView : IMvpView {
    fun setListWallets(wallets: List<Wallet>)
}