package ru.divizdev.homefinance.data

import ru.divizdev.homefinance.entities.Transaction
import ru.divizdev.homefinance.entities.Wallet

interface IRepositoryWallet{
    fun getWallet(key: Int): Wallet?

    fun getListWallet(): Collection<Wallet>

    fun addTransaction(key: Int, transaction: Transaction)


}