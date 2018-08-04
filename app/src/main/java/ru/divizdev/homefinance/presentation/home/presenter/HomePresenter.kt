package ru.divizdev.homefinance.presentation.home.presenter

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.model.OperationInteractor

class HomePresenter(private val operationInteractor: OperationInteractor) : AbstractHomePresenter() {//В дальнейшем получать models необходимо через Фабрику

    override fun update() {
        super.update()

        val view = weakReferenceView.get()

        launch {
            val rubBalance = operationInteractor.getBalance(Currency.RUB)
            val usdBalance = operationInteractor.getBalance(Currency.USD)
            val rubOutcomeOverview = operationInteractor.getBriefOverview(Currency.RUB, OperationType.OUTCOME)
            val rubIncomeOverview = operationInteractor.getBriefOverview(Currency.RUB, OperationType.INCOME)

            launch(UI) {
                view?.setMainBalance(rubBalance)
                view?.setSecondaryBalance(usdBalance)
                view?.setExpense(rubOutcomeOverview)
                view?.setRevenue(rubIncomeOverview)
            }
        }
    }
}