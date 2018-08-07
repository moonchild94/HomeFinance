package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationStatistics
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
import java.util.*

interface RepositoryOperation {

    fun getAll(isPeriodic: Boolean): Flowable<List<Operation>>

    fun queryByWallet(wallet: Wallet, isPeriodic: Boolean): Flowable<List<Operation>>

    fun queryByType(operationType: OperationType): Flowable<List<Operation>>

    fun update(operation: Operation)

    fun delete(operation: Operation)

    fun add(operation: Operation)

    fun getSummaryByCategories(wallet: Wallet, dateFrom: Date, dateTo: Date, operationType: OperationType): Flowable<List<OperationStatistics>>
}