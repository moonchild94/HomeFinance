package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.data.db.dao.WalletDao

class RepositoryWalletImpl(private val walletDao: WalletDao) : RepositoryWallet {
    override fun getWallet(id: Int): Wallet? {
        return walletDao.getById(id)
    }

    override fun getAllWallets(): List<Wallet> {
        return walletDao.getAll()
    }

    override fun deleteWallet(wallet: Wallet) {
        walletDao.delete(wallet)
    }

    override fun addWallet(wallet: Wallet) {
        walletDao.insert(wallet)
    }
}