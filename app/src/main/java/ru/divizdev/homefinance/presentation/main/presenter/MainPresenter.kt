package ru.divizdev.homefinance.presentation.main.presenter

import android.util.Log
import ru.divizdev.homefinance.data.RepositoryCurrencyRate
import ru.divizdev.homefinance.presentation.main.view.IMainView

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

    override fun attachView(view: IMainView) {
        super.attachView(view)

        val repository = RepositoryCurrencyRate()

        repository.loadCurrencyRate({ Log.e("r", it.toString())}, {Log.e("r", it)})
    }
}