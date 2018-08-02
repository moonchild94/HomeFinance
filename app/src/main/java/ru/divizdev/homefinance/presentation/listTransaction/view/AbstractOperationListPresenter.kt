package ru.divizdev.homefinance.presentation.listTransaction.view

import ru.divizdev.homefinance.mvp.BaseMvpPresenter

abstract class AbstractOperationListPresenter : BaseMvpPresenter<IOperationListView>() {
    abstract fun loadOperations()
}