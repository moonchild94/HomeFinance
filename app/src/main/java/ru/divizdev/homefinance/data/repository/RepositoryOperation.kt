package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet

interface RepositoryOperation {

    fun getAll(): Flowable<List<Operation>>

    fun queryByWallet(wallet: Wallet): Flowable<List<Operation>>

    fun queryByType(operationType: OperationType): Flowable<List<Operation>>

    fun update(operation: Operation)

    fun delete(operation: Operation)

    fun add(operation: Operation)
}