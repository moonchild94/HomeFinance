package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.entities.Wallet

class RepositoryWalletImpl(private val walletDao: WalletDao) : RepositoryWallet {
    override fun update(wallet: Wallet) {
        walletDao.update(wallet)
    }

    override fun delete(wallet: Wallet) {
        walletDao.delete(wallet)
    }

    override fun add(wallet: Wallet) {
        walletDao.insert(wallet)
    }

    override fun getById(id: Int): Flowable<Wallet> {
        return walletDao.getById(id)
    }

    override fun getAll(): Flowable<List<Wallet>> {
        return walletDao.getAll()
    }
}