package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Operation

interface RepositoryWalletOperation {
    fun delete(operation: Operation)

    fun add(operation: Operation)
}