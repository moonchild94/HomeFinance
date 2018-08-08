package ru.divizdev.homefinance.mvp

open class BaseMvpChildPresenter<T1 : IMvpView, T2 : IMvpView, ParentPresenter : BaseMvpPresenter<T2>>
(protected val parentPresenter: ParentPresenter) : BaseMvpPresenter<T1>()