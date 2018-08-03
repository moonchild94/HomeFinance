package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationView: IMvpView {
    fun onLoadData(categories: List<String>, wallets: List<String>)

    fun getOperation(): OperationUI

    fun exit()

    fun showErrorObligatoryField()
}