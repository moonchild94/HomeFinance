package ru.divizdev.homefinance.presentation.home.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.model.SummaryInteractor

class HomePresenter(private val summaryInteractor: SummaryInteractor) : AbstractHomePresenter() {

    override fun loadData() {
        summaryInteractor.balanceRUB
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weakReferenceView.get()?.setMainBalance(it) }

        summaryInteractor.balanceUSD
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weakReferenceView.get()?.setSecondaryBalance(it) }

        summaryInteractor.briefOverviewIncome
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weakReferenceView.get()?.setRevenue(it) }

        summaryInteractor.briefOverviewOutcome
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weakReferenceView.get()?.setExpense(it) }
    }
}