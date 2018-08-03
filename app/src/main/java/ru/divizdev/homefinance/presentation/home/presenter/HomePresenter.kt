package ru.divizdev.homefinance.presentation.home.presenter

import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.model.OperationInteractor


class HomePresenter(private val operationInteractor: OperationInteractor): AbstractHomePresenter(){//В дальнейшем получать models необходимо через Фабрику


    override fun update() {
        super.update()
        val view = weakReferenceView.get()
        view?.setMainBalance(operationInteractor.getBalance(Currency.RUB))
        view?.setSecondaryBalance(operationInteractor.getBalance(Currency.USD))
        view?.setExpense(operationInteractor.getBriefOverview(Currency.RUB, OperationType.OUTCOME))
        view?.setRevenue(operationInteractor.getBriefOverview(Currency.RUB, OperationType.INCOME))
    }
}