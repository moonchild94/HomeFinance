package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet

class RepositoryOperationImpl(private val operationDao: OperationDao) : RepositoryOperation {
    override fun queryByOperationType(operationType: OperationType): List<Operation> {
        return operationDao.queryByOperationType(operationType)
    }

    override fun getAll(): List<Operation> {
        return operationDao.getAll()
    }

    override fun queryByWallet(wallet: Wallet): List<Operation> {
        return operationDao.queryByWallet(wallet.walletId)
    }
}