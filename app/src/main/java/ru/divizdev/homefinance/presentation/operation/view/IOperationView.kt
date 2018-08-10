package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.IMvpView

interface IOperationView : IMvpView {
    fun openAddOperation(template: Operation?)
}