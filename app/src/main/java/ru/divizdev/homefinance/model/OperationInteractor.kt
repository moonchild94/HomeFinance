package ru.divizdev.homefinance.model

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet

class OperationInteractor(private val repositoryOperation: RepositoryOperation,
                          private val converter: Converter) {

    fun addOperation(operation: Operation) {
        val mainCurrency = operation.wallet.balance.currency
        if (operation.sumCurrencyOperation.currency != mainCurrency) {
            operation.sumCurrencyMain = converter.convert(operation.sumCurrencyOperation, mainCurrency)
        }
        repositoryOperation.add(operation)
    }

    fun queryOperationsByWallet(wallet: Wallet?, operationType: OperationType): Flowable<List<Operation>> {
        return if (wallet == null) repositoryOperation.getAll(operationType) else
            repositoryOperation.queryByWallet(wallet, operationType)
    }
}