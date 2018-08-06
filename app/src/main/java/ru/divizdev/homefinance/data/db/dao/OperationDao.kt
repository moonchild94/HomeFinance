package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType

/**
 * Dao для работы с транзакциями.
 */
@Dao
interface OperationDao {

    @Update
    fun update(operation: IdleOperation)

    // Удаление IdleOperation из бд
    @Delete
    fun delete(operation: IdleOperation)

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.periodic != 1 ORDER BY IdleOperation.date DESC")
    fun getAll(): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.periodic = 1 ORDER BY IdleOperation.date DESC")
    fun qetAllPeriodic(): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.periodic = 1 ORDER BY IdleOperation.date DESC")
    fun qetAllPeriodicList(): List<Operation>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.walletId = :walletId AND IdleOperation.periodic != 1 ORDER BY IdleOperation.date DESC")
    fun getByWallet(walletId: Int): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.walletId = :walletId AND IdleOperation.periodic = 1 ORDER BY IdleOperation.date DESC")
    fun getPeriodicByWallet(walletId: Int): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.periodic, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE Category.operationType = :operationType AND IdleOperation.periodic != 1")
    fun getByOperationType(operationType: OperationType): Flowable<List<Operation>>
}