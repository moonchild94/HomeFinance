package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Transaction

//Предполагается что в кошельке все транзакции с одной главной валютой. В операции добавления транзакций не забыть вставить конвертор.
class Wallet(val name: String, val mainCurrency: Currency, var listTransactions: List<Transaction>) {

}
