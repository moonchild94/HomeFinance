package ru.divizdev.homefinance.data

import ru.divizdev.homefinance.entities.Wallet

interface IRepositoryWallet{
    fun getWallet(): Wallet
}