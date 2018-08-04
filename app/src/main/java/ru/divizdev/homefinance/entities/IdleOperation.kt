package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
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
                         val categoryId: Int)