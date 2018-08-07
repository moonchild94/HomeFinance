package ru.divizdev.homefinance.entities

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import java.util.*

/**
 * Транзакция.
 */
@Entity(foreignKeys = [
    ForeignKey(
            entity = Wallet::class,
            parentColumns = ["walletId"],
            childColumns = ["walletId"],
            onDelete = CASCADE
    ),
    ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"]
    )]
)
data class IdleOperation(@PrimaryKey(autoGenerate = true) var idleOperationId: Int = 0,
                         val walletId: Int,
                         val comment: String,
                         @Embedded(prefix = "sumMain") val sumCurrencyMain: Money,
                         @Embedded(prefix = "sumOperation") val sumCurrencyOperation: Money = sumCurrencyMain,
                         val date: Date,
                         val categoryId: Int,
                         val operationType: OperationType,
                         val period: Int = 0)