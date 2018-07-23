package ru.divizdev.homefinance.data

import ru.divizdev.homefinance.model.Wallet

interface IRepositoryWallet{
    fun getWallet():Wallet
}