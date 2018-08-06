package ru.divizdev.homefinance.presentation.operationslist.view

import ru.divizdev.homefinance.mvp.BaseMvpPresenter

abstract class AbstractOperationListPresenter : BaseMvpPresenter<IOperationListView>() {
    abstract fun loadOperations(position: Int)

    abstract fun onDeleteOperation(position: Int)

    abstract fun loadWallets()
}