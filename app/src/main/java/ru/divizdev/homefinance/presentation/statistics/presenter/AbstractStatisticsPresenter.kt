package ru.divizdev.homefinance.presentation.statistics.presenter

import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.statistics.view.IStatisticsView
import java.util.*

abstract class AbstractStatisticsPresenter(parentPresenter: AbstractMainPresenter)
    : BaseMvpChildPresenter<IStatisticsView, IMainView, AbstractMainPresenter>(parentPresenter) {

    abstract fun loadStatistics(categoryType: CategoryType, walletPosition: Int, dateFrom: Date, dateTo: Date)

    abstract fun loadWallets()
}