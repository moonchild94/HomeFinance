package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import java.util.*

data class Operation(val operationId: Int,
                     val comment: String,
                     @Embedded(prefix = "sumMain")
                     val sumCurrencyMain: Money,
                     @Embedded(prefix = "sumOperation")
                     val sumCurrencyOperation: Money,
                     val date: Date,
                     @Embedded
                     val wallet: Wallet,
                     @Embedded
                     val category: Category)