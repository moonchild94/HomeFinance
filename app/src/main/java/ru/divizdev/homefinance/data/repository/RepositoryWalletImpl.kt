package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.entities.Wallet

class RepositoryWalletImpl(private val walletDao: WalletDao) : RepositoryWallet {
    override fun delete(wallet: Wallet) {
        walletDao.delete(wallet)
    }

    override fun add(wallet: Wallet) {
        walletDao.insert(wallet)
    }

    override fun getById(id: Int): Wallet? {
        return walletDao.getById(id)
    }

    override fun getAll(): List<Wallet> {
        return walletDao.getAll()
    }
}