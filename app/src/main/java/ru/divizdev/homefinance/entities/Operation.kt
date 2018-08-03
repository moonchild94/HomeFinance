package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import java.util.*

data class Operation(val operationId: Int = 0,
                     val comment: String,
                     @Embedded(prefix = "sumOperation")
                     var sumCurrencyOperation: Money,
                     @Embedded(prefix = "sumMain")
                     var sumCurrencyMain: Money = sumCurrencyOperation,
                     val date: Date,
                     @Embedded
                     val wallet: Wallet,
                     @Embedded
                     val category: Category)