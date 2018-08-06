package ru.divizdev.homefinance.model

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
import java.util.*

class OperationInteractor(private val repositoryOperation: RepositoryOperation,
                          private val converter: Converter) {

    fun addOperation(operation: Operation) {
        val mainCurrency = operation.wallet.balance.currency
        if (operation.sumCurrencyOperation.currency != mainCurrency) {
            operation.sumCurrencyMain = converter.convert(operation.sumCurrencyOperation, mainCurrency)
        }
        repositoryOperation.add(operation)
    }

    fun getAllOperations(isPeriodic: Boolean): Flowable<List<Operation>> {
        return repositoryOperation.getAll(isPeriodic)
    }

    fun queryOperationsByWallet(wallet: Wallet, isPeriodic: Boolean): Flowable<List<Operation>> {
        return repositoryOperation.queryByWallet(wallet, isPeriodic)
    }

    fun queryOperationsByType(operationType: OperationType): Flowable<List<Operation>> {
        return repositoryOperation.queryByType(operationType)
    }
}