package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import java.util.*

data class OperationUI(val date: Date,
                       val time: Date,
                       val operationType: OperationType,
                       val category: String,
                       val walletKey: Int,
                       val value: Float?,
                       val currency: Currency)