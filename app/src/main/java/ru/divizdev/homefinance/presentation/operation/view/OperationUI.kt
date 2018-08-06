package ru.divizdev.homefinance.presentation.operation.view

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import java.util.*

data class OperationUI(val date: Date,
                       val time: Date,
                       val operationType: OperationType,
                       val categoryNumber: Int,
                       val walletNumber: Int,
                       val value: Double?,
                       val currency: Currency,
                       val comment: String,
                       val period: Int)