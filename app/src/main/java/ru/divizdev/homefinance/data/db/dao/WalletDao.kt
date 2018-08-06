package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Wallet

/**
 * Dao для работы с кошельками.
 */
@Dao
interface WalletDao {

    @Insert
    fun insert(wallet: Wallet)

    @Delete
    fun delete(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Query("SELECT * FROM wallet WHERE walletId = :id")
    fun getById(id: Int): Flowable<Wallet>

    @Query("SELECT * FROM wallet")
    fun getAll(): Flowable<List<Wallet>>
}