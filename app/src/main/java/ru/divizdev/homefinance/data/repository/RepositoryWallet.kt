package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Wallet

interface RepositoryWallet {

    fun getById(id: Int): Wallet?

    fun getAll(): List<Wallet>

    fun add(wallet: Wallet)

    fun delete(wallet: Wallet)
}