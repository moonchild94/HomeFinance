package ru.divizdev.homefinance.presentation.main.presenter

import android.util.Log
import ru.divizdev.homefinance.data.RepositoryCurrencyRate
import ru.divizdev.homefinance.presentation.main.view.IMainView

class MainPresenter : AbstractMainPresenter() {
    override fun actionShowAddTransaction() {
        val view = weakReferenceView.get()
        view?.openAddTransaction()
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

    override fun actionNavigationListOperation(): Boolean {
        val view = weakReferenceView.get()
        if (view?.getOpenTypeScreen() != TypeSubScreen.OPERATIONS) {
            view?.openTransactions()
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

        repository.loadCurrencyRate({ Log.e("r", it.toString()) }, { Log.e("r", it) })
    }
}