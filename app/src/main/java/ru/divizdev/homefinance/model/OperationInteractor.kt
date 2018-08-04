package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWalletOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
import java.util.*

private const val MS_IN_DAY = 86400000

class OperationInteractor(private val repositoryWalletOperation: RepositoryWalletOperation,
                          private val repositoryOperation: RepositoryOperation,
                          private val converter: Converter) {

    private var nearestExecuteDate: Date = Date()

    fun addOperation(operation: Operation) {
        val mainCurrency = operation.wallet.balance.currency
        if (operation.sumCurrencyOperation.currency != mainCurrency) {
            operation.sumCurrencyMain = converter.convert(operation.sumCurrencyOperation, mainCurrency)
        }
        repositoryWalletOperation.add(operation)
        checkPeriodicOperations()
    }

    fun getAllOperations(): List<Operation> {
        checkPeriodicOperations()
        return repositoryOperation.getAll()
    }

    fun queryOperationsByWallet(wallet: Wallet): List<Operation> {
        checkPeriodicOperations()
        return repositoryOperation.queryByWallet(wallet)
    }

    fun queryOperationsByType(operationType: OperationType): List<Operation> {
        checkPeriodicOperations()
        return repositoryOperation.queryByOperationType(operationType)
    }

    private fun checkPeriodicOperations() {
        val now = Date()
        if (now > nearestExecuteDate) {
            val periodicOperations = repositoryOperation.queryPeriodic()
            var newNearestExecuteDate = Date(Long.MAX_VALUE)

            for (periodicOperation in periodicOperations) {
                if (periodicOperation.date <= nearestExecuteDate) {

                    val count = (now.time - periodicOperation.date.time) / (periodicOperation.period * MS_IN_DAY)
                    for (i in 0 until count) {
                        addOperation(periodicOperation.copy(pending = false))
                        periodicOperation.date = Date(periodicOperation.date.time + MS_IN_DAY * periodicOperation.period * (i + 1))
                    }

                    if (newNearestExecuteDate > periodicOperation.date) {
                        newNearestExecuteDate = periodicOperation.date
                    }

                    repositoryOperation.update(periodicOperation)
                }
            }

            nearestExecuteDate = newNearestExecuteDate
        }
    }
}