package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationView: IMvpView {
    fun getOperation(): OperationUI

    fun exit()

    fun showErrorObligatoryField()
}