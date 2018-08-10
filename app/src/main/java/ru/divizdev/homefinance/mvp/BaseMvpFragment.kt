package ru.divizdev.homefinance.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMvpFragment<Presenter: BaseMvpChildPresenter<MvpView, MvpParentView, ParentPresenter>,
        MvpView: IMvpView, MvpParentView: IMvpView, ParentPresenter : BaseMvpPresenter<MvpParentView>>: Fragment(), IMvpView{

    protected lateinit var presenter: Presenter
    protected abstract fun getInstancePresenter(): Presenter

    abstract fun getMvpView(): MvpView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(getMvpView())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is BaseMvpActivity<*, *>) {
            throw IllegalArgumentException()
        }
        presenter = getInstancePresenter()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}