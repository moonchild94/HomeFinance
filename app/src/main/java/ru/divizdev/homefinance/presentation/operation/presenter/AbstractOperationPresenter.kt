package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.operation.view.IOperationView

abstract class AbstractOperationPresenter: BaseMvpPresenter<IOperationView>() {

    abstract fun save()

}