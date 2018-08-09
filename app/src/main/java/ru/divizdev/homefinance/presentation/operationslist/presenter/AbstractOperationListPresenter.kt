package ru.divizdev.homefinance.presentation.operationslist.presenter

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.operationslist.view.IOperationListView

abstract class AbstractOperationListPresenter(parentPresenter: AbstractMainPresenter) : BaseMvpChildPresenter<IOperationListView, IMainView,
        AbstractMainPresenter>(parentPresenter) {
    abstract fun loadOperations(position: Int, isPeriodic: Boolean)

    abstract fun loadWallets()

    abstract fun getOperation(position: Int) : Operation
}