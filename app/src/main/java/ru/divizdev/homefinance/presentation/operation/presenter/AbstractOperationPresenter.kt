package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.operation.view.IOperationView

abstract class AbstractOperationPresenter: BaseMvpPresenter<IOperationView>() {

    abstract fun save()

    abstract fun loadWallets()

    abstract fun loadCategories(operationType: OperationType)
}