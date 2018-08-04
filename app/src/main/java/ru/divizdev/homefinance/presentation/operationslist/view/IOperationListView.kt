package ru.divizdev.homefinance.presentation.operationslist.view

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationListView : IMvpView {
    fun showOperationList(operationList: List<Operation>)

    fun showDeleteFragmentDialog(position: Int)

    fun showWalletsSpinner(wallets: List<String>)
}