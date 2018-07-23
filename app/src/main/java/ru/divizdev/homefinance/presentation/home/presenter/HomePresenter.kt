package ru.divizdev.homefinance.presentation.home.presenter

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.model.UserWalletManager
import ru.divizdev.homefinance.presentation.home.view.IHomeView
import java.lang.ref.WeakReference


class HomePresenter(private val userWalletManager: UserWalletManager = UserWalletManager()): IHomePresenter{//В дальнейшем получать models необходимо через Фабрику


    private var weakReferenceView: WeakReference<IHomeView>? = null

    override fun attachView(view: IHomeView) {
        weakReferenceView = WeakReference(view)
        view.setMainBalance(userWalletManager.getBalance(Currency.RUB))
        view.setSecondaryBalance(userWalletManager.getBalance(Currency.USD))
        view.setExpense(userWalletManager.getBriefOverview(TypeTransaction.Expense))
        view.setRevenue(userWalletManager.getBriefOverview(TypeTransaction.Revenue))
    }

    override fun detachView() {
        weakReferenceView?.clear()
    }


}