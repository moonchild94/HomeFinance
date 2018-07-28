package ru.divizdev.homefinance.presentation.main.presenter

class MainPresenter: AbstractMainPresenter() {


    override fun actionShowAbout() {
        val view = weakReferenceView.get()
        view?.showAboutDialog()
    }

    override fun actionShowSettings() {
        val view = weakReferenceView.get()
        view?.showSettingsDialog()
    }

    override fun actionNavigationListOperation():Boolean {
        val view = weakReferenceView.get()
        view?.showErrorNotAvailable()
        return false
    }

    override fun actionNavigationAccount():Boolean {
        val view = weakReferenceView.get()
        view?.showErrorNotAvailable()
        return false
    }


}