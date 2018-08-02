package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.entities.Operation

class RepositoryOperationImpl(private val operationDao: OperationDao) : RepositoryOperation {
    override fun getAllOperations(): Collection<Operation> {
       return operationDao.getAll()
    }

    override fun deleteOperation(operation: Operation) {
        operationDao.delete()
    }

    override fun addOperation(operation: Operation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOperation(id: Int): Operation? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}