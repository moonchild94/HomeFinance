package ru.divizdev.homefinance.presentation.home.view

import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.mvp.IMvpView

interface IHomeView: IMvpView {
    fun setMainBalance(balance: Money)
    fun setSecondaryBalance(balance: Money)
    fun setRevenue(balance: Money)
    fun setExpense(balance: Money)
}