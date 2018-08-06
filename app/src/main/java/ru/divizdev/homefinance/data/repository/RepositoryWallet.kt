package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Wallet

interface RepositoryWallet {

    fun getById(id: Int): Flowable<Wallet>

    fun getAll(): Flowable<List<Wallet>>

    fun add(wallet: Wallet)

    fun delete(wallet: Wallet)

    fun update(wallet: Wallet)
}