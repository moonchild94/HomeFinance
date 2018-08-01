package ru.divizdev.homefinance.entities


import java.util.*

data class Transaction(val typeTransaction: TypeTransaction,
                       val sumCurrencyMain: Money,
                       val sumCurrencyOperation: Money = sumCurrencyMain,
                       val metadataTransaction: MetadataTransaction = MetadataTransaction(Date(), ""))
