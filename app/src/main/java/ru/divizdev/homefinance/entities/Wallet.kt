package ru.divizdev.homefinance.entities

//Предполагается что в кошельке все транзакции с одной главной валютой. При добавлении транзакции будет осуществляться конвертация в валюту кошелька
class Wallet(val name: String, val mainCurrency: Currency, var listTransactions: List<Transaction>) {

}
