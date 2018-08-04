package ru.divizdev.homefinance.mvp

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMvpDialogFragment<Presenter: BaseMvpPresenter<MvpView>, MvpView: IMvpView>: DialogFragment(), IMvpView{

    protected val presenter = getInstancePresenter()
    protected abstract fun getInstancePresenter(): Presenter
    abstract fun getMvpView(): MvpView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(getMvpView())
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        presenter.update()
    }
}