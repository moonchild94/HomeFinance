package ru.divizdev.homefinance.presentation.statistics.presenter

import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.statistics.view.IStatisticsView
import java.util.*

abstract class AbstractStatisticsPresenter : BaseMvpPresenter<IStatisticsView>() {
    abstract fun loadStatistics(operationType: OperationType, walletPosition: Int, dateFrom: Date, dateTo: Date)

    abstract fun loadWallets()
}