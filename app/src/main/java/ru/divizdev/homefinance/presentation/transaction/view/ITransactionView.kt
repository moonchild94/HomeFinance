package ru.divizdev.homefinance.presentation.transaction.view

import ru.divizdev.homefinance.mvp.IMvpView

interface ITransactionView: IMvpView {
    fun getTransaction(): TransactionUI

    fun exit()

    fun showErrorObligatoryField()
}