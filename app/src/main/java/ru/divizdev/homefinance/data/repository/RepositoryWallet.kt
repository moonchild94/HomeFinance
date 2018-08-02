package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Wallet

interface RepositoryWallet {

    fun getWallet(id: Int): Wallet?

    fun getAllWallets(): List<Wallet>

    fun deleteWallet(wallet: Wallet)

    fun addWallet(wallet: Wallet)
}