package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Operation(val operationId: Int = 0,
                     val comment: String,
                     @Embedded(prefix = "sumOperation")
                     var sumCurrencyOperation: Money,
                     @Embedded(prefix = "sumMain")
                     var sumCurrencyMain: Money = sumCurrencyOperation,
                     var date: Date,
                     @Embedded
                     val wallet: Wallet,
                     @Embedded
                     val category: Category,
                     val operationType: OperationType,
                     val period: Int = 0) : Parcelable