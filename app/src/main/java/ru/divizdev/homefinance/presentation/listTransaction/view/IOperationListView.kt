package ru.divizdev.homefinance.presentation.listTransaction.view

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationListView: IMvpView {
    fun showOperationList(operationList: List<Operation>)
}