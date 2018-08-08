package ru.divizdev.homefinance.mvp

import java.lang.ref.WeakReference

abstract class BaseMvpPresenter<T: IMvpView> {

    protected var weakReferenceView: WeakReference<T?> = WeakReference(null)

    open fun attachView(view: T){
        weakReferenceView = WeakReference(view)
    }

    open fun detachView(){
        weakReferenceView.clear()
    }

    open fun attach() {
    }

    open fun detach() {
    }
}