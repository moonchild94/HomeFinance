package ru.divizdev.homefinance.presentation.main.view

import ru.divizdev.homefinance.mvp.IMvpView

interface IMainView: IMvpView {

    fun showAboutDialog()

    fun showSettingsDialog()

    fun showErrorNotAvailable()
}