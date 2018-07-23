package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Transaction

//Предполагается что в кошельке все транзакции с одной главной валютой.
class Wallet(val name: String, val mainCurrency: Currency, var listTransactions: List<Transaction>) {

}
