package ru.divizdev.homefinance.presentation.statistics.view

import ru.divizdev.homefinance.entities.OperationStatistics
import ru.divizdev.homefinance.mvp.IMvpView

interface IStatisticsView : IMvpView {
    fun showStatistics(statisticsValues: List<OperationStatistics>)

    fun showWallets(wallets: List<String>)
}