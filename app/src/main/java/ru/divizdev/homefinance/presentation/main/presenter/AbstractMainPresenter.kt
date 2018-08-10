package ru.divizdev.homefinance.presentation.main.presenter

import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView

abstract class AbstractMainPresenter: BaseMvpPresenter<IMainView>() {

    abstract fun actionShowAbout()

    abstract fun actionShowAddOperation()

    abstract fun actionShowSettings()

    abstract fun actionShowStatistics()

    abstract fun actionNavigationListOperation(): Boolean

    abstract fun actionNavigationAccount():Boolean

    abstract fun actionNavigationHome():Boolean

}