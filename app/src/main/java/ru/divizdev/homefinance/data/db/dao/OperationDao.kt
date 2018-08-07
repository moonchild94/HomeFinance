package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationStatistics
import ru.divizdev.homefinance.entities.CategoryType
import java.util.*

/**
 * Dao для работы с транзакциями.
 */
@Dao
interface OperationDao {

    @Update
    fun update(operation: IdleOperation)

    @Delete
    fun delete(operation: IdleOperation)

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.operationType = 2 ORDER BY IdleOperation.date DESC")
    fun getAll(): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.operationType = 0 ORDER BY IdleOperation.date DESC")
    fun qetAllPeriodic(): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.operationType = 0 ORDER BY IdleOperation.date DESC")
    fun qetAllPeriodicList(): List<Operation>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.walletId = :walletId AND IdleOperation.operationType = 2 ORDER BY IdleOperation.date DESC")
    fun getByWallet(walletId: Int): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.walletId = :walletId AND IdleOperation.operationType = 0 ORDER BY IdleOperation.date DESC")
    fun getPeriodicByWallet(walletId: Int): Flowable<List<Operation>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE Category.categoryType = :categoryType AND IdleOperation.operationType = 2")
    fun getByOperationType(categoryType: CategoryType): Flowable<List<Operation>>

    @Query("""SELECT Category.*, SUM(IdleOperation.sumMainamount) amount
        from IdleOperation
        INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId
        WHERE IdleOperation.operationType = 2 and IdleOperation.walletId = :walletId and IdleOperation.date <= :dateTo
        and IdleOperation.date >= :dateFrom and Category.categoryType = :categoryType GROUP BY Category.categoryId""")
    fun getOperationsSummaryByCategories(walletId: Int, dateFrom: Date, dateTo: Date, categoryType: CategoryType): Flowable<List<OperationStatistics>>

    @Query("SELECT IdleOperation.idleOperationId as operationId, IdleOperation.comment, IdleOperation.date, IdleOperation.sumMainamount, IdleOperation.sumMaincurrency, IdleOperation.sumOperationamount, IdleOperation.sumOperationcurrency, IdleOperation.operationType, IdleOperation.period, Category.*, Wallet.* FROM IdleOperation INNER JOIN Category ON IdleOperation.categoryId = Category.categoryId INNER JOIN WALLET ON IdleOperation.walletId = wallet.walletId WHERE IdleOperation.operationType = 1")
    fun getTemplates(): Flowable<List<Operation>>
}