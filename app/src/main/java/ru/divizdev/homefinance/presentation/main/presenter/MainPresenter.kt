package ru.divizdev.homefinance.presentation.main.presenter

import ru.divizdev.homefinance.data.repository.RepositoryCurrencyRate
import ru.divizdev.homefinance.presentation.main.view.IMainView

class MainPresenter : AbstractMainPresenter() {

    override fun actionShowAddOperation() {
        val view = weakReferenceView.get()
        view?.openAddOperation()
    }

    override fun actionNavigationHome(): Boolean {
        val view = weakReferenceView.get()
        if(view?.getOpenTypeScreen() != TypeSubScreen.HOME) {
            view?.openHome()
            return true
        }
        return false
    }

    override fun actionShowAbout() {
        val view = weakReferenceView.get()
        view?.showAboutDialog()
    }

    override fun actionShowSettings() {
        val view = weakReferenceView.get()
        view?.showSettingsDialog()
    }

    override fun actionShowStatistics() {
        weakReferenceView.get()?.showStatisticsDialog()
    }

    override fun actionNavigationListOperation(): Boolean {
        val view = weakReferenceView.get()
        if (view?.getOpenTypeScreen() != TypeSubScreen.OPERATIONS) {
            view?.openOperations()
            return true
        }
        return false
    }

    override fun actionNavigationAccount(): Boolean {
        val view = weakReferenceView.get()
        if (view?.getOpenTypeScreen() != TypeSubScreen.WALLETS) {
            view?.openWallets()
            return true
        }
        return false
    }

    override fun attachView(view: IMainView) {
        super.attachView(view)

        val repository = RepositoryCurrencyRate()

        //запускаем загрузку рейтинга
        repository.loadCurrencyRate()
    }
}