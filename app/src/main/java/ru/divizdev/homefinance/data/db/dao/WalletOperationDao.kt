package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.*
import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Wallet

@Dao
interface WalletOperationDao {

    // Редактирование Wallet в бд
    @Update
    fun update(wallet: Wallet)

    // Добавление IdleOperation в бд
    @Insert
    fun insert(operation: IdleOperation)

    // Удаление IdleOperation из бд
    @Delete
    fun delete(operation: IdleOperation)

    @Transaction
    fun insertOperationAndUpdateWallet(operation: IdleOperation, wallet: Wallet) {
        insert(operation)
        update(wallet)
    }

    @Transaction
    fun deleteOperationAndUpdateWallet(operation: IdleOperation, wallet: Wallet) {
        delete(operation)
        update(wallet)
    }
}