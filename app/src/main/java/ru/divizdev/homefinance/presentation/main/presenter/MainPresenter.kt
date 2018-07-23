package ru.divizdev.homefinance.presentation.main.presenter

import ru.divizdev.homefinance.presentation.main.view.IMainView
import java.lang.ref.WeakReference

class MainPresenter: IMainPresenter{

    private var weakReferenceView:WeakReference<IMainView>? = null

    override fun attachView(view: IMainView) {
       weakReferenceView = WeakReference(view)
    }

    override fun detachView() {
        weakReferenceView?.clear()
    }

    override fun actionShowAbout() {
        val view = weakReferenceView?.get()
        view?.showAboutDialog()
    }

    override fun actionShowSettings() {
        val view = weakReferenceView?.get()
        view?.showSettingsDialog()
    }

    override fun actionNavigationListOperation():Boolean {
        val view = weakReferenceView?.get()
        view?.showErrorNotAvailable()
        return false
    }

    override fun actionNavigationAccount():Boolean {
        val view = weakReferenceView?.get()
        view?.showErrorNotAvailable()
        return false
    }


}