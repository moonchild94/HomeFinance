package ru.divizdev.homefinance.presentation.home.presenter

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.model.UserWalletManager


class HomePresenter(private val userWalletManager: UserWalletManager = UserWalletManager()): AbstractHomePresenter(){//В дальнейшем получать models необходимо через Фабрику


    override fun update() {
        super.update()
        val view = weakReferenceView.get()
        view?.setMainBalance(userWalletManager.getBalance(Currency.RUB))
        view?.setSecondaryBalance(userWalletManager.getBalance(Currency.USD))
        view?.setExpense(userWalletManager.getBriefOverview(Currency.RUB, TypeTransaction.Expense))
        view?.setRevenue(userWalletManager.getBriefOverview(Currency.RUB, TypeTransaction.Revenue))
    }
}