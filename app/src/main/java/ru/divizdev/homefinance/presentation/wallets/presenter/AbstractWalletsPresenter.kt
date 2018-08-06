package ru.divizdev.homefinance.presentation.wallets.presenter

import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.wallets.ui.IWalletsView

abstract class AbstractWalletsPresenter : BaseMvpPresenter<IWalletsView>() {

    abstract fun loadData()

    abstract fun onDeleteOperation(position: Int)

    abstract fun onEditOperation(position: Int, newName: String)
}