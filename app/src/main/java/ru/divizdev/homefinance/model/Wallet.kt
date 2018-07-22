package ru.divizdev.homefinance.model

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Transaction

class Wallet(val mainCurrency: Currency, val listTransactions: List<Transaction>) {

}
