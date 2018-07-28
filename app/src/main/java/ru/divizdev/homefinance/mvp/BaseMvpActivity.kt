package ru.divizdev.homefinance.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMvpActivity<Presenter : BaseMvpPresenter<MvpView>, MvpView : IMvpView> : AppCompatActivity(), IMvpView {

    protected val presenter = getInstancePresenter()
    protected abstract fun getInstancePresenter(): Presenter
    abstract fun getMvpView(): MvpView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(getMvpView())
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}