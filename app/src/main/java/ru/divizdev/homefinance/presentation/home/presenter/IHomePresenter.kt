package ru.divizdev.homefinance.presentation.home.presenter

import ru.divizdev.homefinance.presentation.home.view.IHomeView

interface IHomePresenter {
    fun attachView(view: IHomeView)

    fun detachView()
}