package ru.divizdev.homefinance.presentation.main.view

import ru.divizdev.homefinance.mvp.IMvpView
import ru.divizdev.homefinance.presentation.main.presenter.TypeSubScreen

interface IMainView: IMvpView {

    fun showAboutDialog()

    fun showSettingsDialog()

    fun showStatisticsDialog()

    fun showErrorNotAvailable()

    fun openAddOperation()

    fun openHome()

    fun openWallets()

    fun getOpenTypeScreen(): TypeSubScreen

    fun openOperations()
}