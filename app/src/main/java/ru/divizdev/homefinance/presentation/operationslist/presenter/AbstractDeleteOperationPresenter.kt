package ru.divizdev.homefinance.presentation.operationslist.presenter

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.operationslist.view.IDeleteOperationView

abstract class AbstractDeleteOperationPresenter : BaseMvpPresenter<IDeleteOperationView>() {

    abstract fun onDeleteOperation(operation: Operation)
}