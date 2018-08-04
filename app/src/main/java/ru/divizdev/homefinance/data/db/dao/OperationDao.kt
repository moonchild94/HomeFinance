package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType

/**
 * Dao для работы с транзакциями.
 */
@Dao
interface OperationDao {

    // Удаление IdleOperation из бд
    @Delete
    fun delete(operation: IdleOperation)

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId")
    fun getAll(): List<Operation>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.walletId = :walletId")
    fun queryByWallet(walletId: Int): List<Operation>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE Category.operationType = :operationType")
    fun queryByOperationType(operationType: OperationType): List<Operation>
}