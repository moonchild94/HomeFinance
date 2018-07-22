package ru.divizdev.homefinance.presentation.main.presenter

import ru.divizdev.homefinance.presentation.main.view.IMainView

interface IMainPresenter {

    fun attachView(view: IMainView)

    fun detachView()

    fun actionShowAbout()

    fun actionShowSettings()

    fun actionNavigationListOperation(): Boolean

    fun actionNavigationAccount():Boolean

}