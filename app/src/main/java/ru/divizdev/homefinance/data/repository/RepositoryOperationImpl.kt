package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.data.mapper.OperationMapper
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
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

    override fun getAll(): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return operationDao.getAll()
    }

    override fun queryByWallet(wallet: Wallet): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return operationDao.queryByWallet(wallet.walletId)
    }

    override fun queryByType(operationType: OperationType): Flowable<List<Operation>> {
        if (Date() > nearestExecuteDate) {
            handlePeriodicOperations()
        }

        return operationDao.queryByOperationType(operationType)
    }

    override fun update(operation: Operation) {
        operationDao.update(OperationMapper.mapOperationToIdleOperation(operation))
    }

    private fun calculateDiff(operation: Operation, operationAmount: BigDecimal) :BigDecimal {
        return if (operation.category.operationType == OperationType.INCOME) operationAmount else operationAmount.negate()
    }

    private fun handlePeriodicOperations() {
        val now = Date()
        val periodicOperations = operationDao.queryPeriodic()
        var newNearestExecuteDate = Date(Long.MAX_VALUE)

        for (periodicOperation in periodicOperations) {
            if (periodicOperation.date <= now) {

                val count = (now.time - periodicOperation.date.time) / (periodicOperation.period * MS_IN_DAY)
                for (i in 0 until count) {
                    add(periodicOperation.copy(periodic = false, period = 0))
                    periodicOperation.date = Date(periodicOperation.date.time + MS_IN_DAY * periodicOperation.period * i)
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