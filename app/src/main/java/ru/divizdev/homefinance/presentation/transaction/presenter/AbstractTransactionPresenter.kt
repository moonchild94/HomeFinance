package ru.divizdev.homefinance.presentation.transaction.presenter

import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.transaction.view.ITransactionView

abstract class AbstractTransactionPresenter: BaseMvpPresenter<ITransactionView>() {

    abstract fun save()

}