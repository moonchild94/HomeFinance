package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Operation

interface RepositoryOperation {
    fun getOperation(id: Int): Operation?

    fun getAllOperations(): List<Operation>

    fun deleteOperation(operation: Operation)

    fun addOperation(operation: Operation)
}