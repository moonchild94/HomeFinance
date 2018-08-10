package ru.divizdev.homefinance.presentation.home.presenter

import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.presentation.home.view.IHomeView
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView

abstract class AbstractHomePresenter(parentPresenter: AbstractMainPresenter) : BaseMvpChildPresenter<IHomeView, IMainView,
        AbstractMainPresenter>(parentPresenter) {
    abstract fun loadData()
}