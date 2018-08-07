package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.data.mapper.OperationMapper
import ru.divizdev.homefinance.entities.*
import java.math.BigDecimal
import java.util.*

private const val MS_IN_DAY = 86400000

class RepositoryOperationImpl(private val operationDao: OperationDao,
                              private val walletOperationDao: WalletOperationDao) : RepositoryOperation {

    private var nearestExecuteDate: Date = Date()

    override fun add(operation: Operation) {
        val idleOperation = OperationMapper.mapOperationToIdleOperation(operation)
        val operationAmount = idleOperation.sumCurrencyMain.amount
        operation.wallet.balance.amount =
                operation.wallet.balance.amount.plus(calculateDiff(operation, operationAmount))
        walletOperationDao.insertOperationAndUpdateWallet(idleOperation, operation.wallet)

        handlePeriodicOperations()
    }

    override fun delete(operation: Operation) {
        val idleOperation = OperationMapper.mapOperationToIdleOperation(operation)
        val operationAmount = idleOperation.sumCurrencyMain.amount
        operation.wallet.balance.amount =
                operation.wallet.balance.amount.minus(calculateDiff(operation, operationAmount))
        walletOperationDao.deleteOperationAndUpdateWallet(idleOperation, operation.wallet)
    }

    override fun getAll(isPeriodic: Boolean): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return if (isPeriodic) operationDao.qetAllPeriodic() else operationDao.getAll()
    }

    override fun queryByWallet(wallet: Wallet, isPeriodic: Boolean): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return if (isPeriodic) operationDao.getPeriodicByWallet(wallet.walletId) else operationDao.getByWallet(wallet.walletId)
    }

    override fun queryByType(categoryType: CategoryType): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return operationDao.getByOperationType(categoryType)
    }

    override fun update(operation: Operation) {
        operationDao.update(OperationMapper.mapOperationToIdleOperation(operation))
    }

    override fun getSummaryByCategories(wallet: Wallet, dateFrom: Date, dateTo: Date, categoryType: CategoryType): Flowable<List<OperationStatistics>> {
        return operationDao.getOperationsSummaryByCategories(wallet.walletId, dateFrom, dateTo, categoryType)
    }

    override fun getTemplates(): Flowable<List<Operation>> {
        return operationDao.getTemplates()
    }

    private fun calculateDiff(operation: Operation, operationAmount: BigDecimal): BigDecimal {
        return if (operation.category.categoryType == CategoryType.INCOME) operationAmount else operationAmount.negate()
    }

    private fun handlePeriodicOperations() {
        val now = Date()
        val periodicOperations = operationDao.qetAllPeriodicList() // todo придумать, как через rx
        var newNearestExecuteDate = Date(Long.MAX_VALUE)

        for (periodicOperation in periodicOperations) {
            if (periodicOperation.date <= now) {

                val count = (now.time - periodicOperation.date.time) / (periodicOperation.period * MS_IN_DAY)
                for (i in 0 until count) {
                    add(periodicOperation.copy(operationType = OperationType.COMPLETE, period = 0))
                    periodicOperation.date = Date(periodicOperation.date.time + MS_IN_DAY * periodicOperation.period * (i + 1))
                }

                if (newNearestExecuteDate > periodicOperation.date) {
                    newNearestExecuteDate = periodicOperation.date
                }

                operationDao.update(OperationMapper.mapOperationToIdleOperation(periodicOperation))
            }
        }

        nearestExecuteDate = newNearestExecuteDate
    }
}