package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.data.mapper.OperationMapper
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType

class RepositoryWalletOperationImpl(private val walletOperationDao: WalletOperationDao) : RepositoryWalletOperation {

    override fun add(operation: Operation) {
        val idleOperation = OperationMapper.mapOperationToIdleOperation(operation)
        val operationAmount = idleOperation.sumCurrencyMain.amount
        operation.wallet.balance.amount =
                operation.wallet.balance.amount.plus(if (operation.category.operationType == OperationType.INCOME) operationAmount else operationAmount.negate())
        walletOperationDao.insertOperationAndUpdateWallet(idleOperation, operation.wallet)
    }

    override fun delete(operation: Operation) {
        val idleOperation = OperationMapper.mapOperationToIdleOperation(operation)
        walletOperationDao.deleteOperationAndUpdateWallet(idleOperation, operation.wallet)
    }
}