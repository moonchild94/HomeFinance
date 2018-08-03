package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.Wallet

interface RepositoryOperation {

    fun getAll(): List<Operation>

    fun query(wallet: Wallet): List<Operation>
}