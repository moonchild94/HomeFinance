package ru.divizdev.homefinance.presentation.transaction.view

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.TypeTransaction
import java.util.*

data class TransactionUI(val date: Date,
                         val time: Date,
                         val typeTransaction: TypeTransaction,
                         val category: String,
                         val walletKey: Int,
                         val value: Float?,
                         val currency: Currency)