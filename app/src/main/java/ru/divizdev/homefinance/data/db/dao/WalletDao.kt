package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.divizdev.homefinance.entities.Wallet

/**
 * Dao для работы с кошельками.
 */
@Dao
interface WalletDao {

    // Добавление Wallet в бд
    @Insert
    fun insert(wallet: Wallet)

    // Удаление Wallet из бд
    @Delete
    fun delete(wallet: Wallet)

    // Получение Wallet по идентификатору
    @Query("SELECT * FROM wallet WHERE walletId = :id")
    fun getById(id: Int): Wallet?

    // Получение всех Wallet из бд
    @Query("SELECT * FROM wallet")
    fun getAll(): List<Wallet>
}