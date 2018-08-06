package ru.divizdev.homefinance.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMvpFragment<Presenter: BaseMvpPresenter<MvpView>, MvpView: IMvpView>: Fragment(), IMvpView{

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
}