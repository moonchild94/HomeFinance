package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet

interface RepositoryOperation {

    fun getAll(): List<Operation>

    fun queryByWallet(wallet: Wallet): List<Operation>

    fun queryByOperationType(operationType: OperationType): List<Operation>
}