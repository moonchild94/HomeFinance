package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationView: IMvpView {
    fun onLoadWallets(wallets: List<String>)

    fun onLoadCategories(categories: List<String>)

    fun getOperation(): OperationUI

    fun exit()

    fun showErrorObligatoryField()
}