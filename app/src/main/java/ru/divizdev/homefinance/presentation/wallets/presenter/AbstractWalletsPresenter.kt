package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.wallets.ui.IWalletsView

abstract class AbstractWalletsPresenter(parentPresenter: AbstractMainPresenter) : BaseMvpChildPresenter<IWalletsView, IMainView,
        AbstractMainPresenter>(parentPresenter) {

    abstract fun loadData()

    abstract fun getWallet(position: Int) : Wallet
}