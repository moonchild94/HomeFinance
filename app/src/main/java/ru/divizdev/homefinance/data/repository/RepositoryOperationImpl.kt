package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.Wallet

class RepositoryOperationImpl(private val operationDao: OperationDao) : RepositoryOperation {

    override fun getAll(): List<Operation> {
        return operationDao.getAll()
    }

    override fun query(wallet: Wallet): List<Operation> {
        return operationDao.query(wallet.walletId)
    }
}