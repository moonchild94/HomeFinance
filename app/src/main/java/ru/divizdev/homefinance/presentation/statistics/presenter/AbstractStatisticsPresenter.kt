package ru.divizdev.homefinance.presentation.statistics.presenter

import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.statistics.view.IStatisticsView
import java.util.*

abstract class AbstractStatisticsPresenter : BaseMvpPresenter<IStatisticsView>() {
    abstract fun loadStatistics(categoryType: CategoryType, walletPosition: Int, dateFrom: Date, dateTo: Date)

    abstract fun loadWallets()
}