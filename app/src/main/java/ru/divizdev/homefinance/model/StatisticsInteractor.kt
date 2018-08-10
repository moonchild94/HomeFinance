package ru.divizdev.homefinance.model

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.OperationStatistics
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.Wallet
import java.util.*

class StatisticsInteractor(private val repositoryOperation: RepositoryOperation) {
    fun getSummaryByCategories(wallet: Wallet, dateFrom: Date, dateTo: Date, categoryType: CategoryType): Flowable<List<OperationStatistics>> {
        return repositoryOperation.getSummaryByCategories(wallet, dateFrom, dateTo, categoryType)
    }
}