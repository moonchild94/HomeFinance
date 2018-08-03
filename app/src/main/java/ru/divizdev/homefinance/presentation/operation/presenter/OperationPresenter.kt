package ru.divizdev.homefinance.presentation.operation.presenter

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryCategory
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.model.OperationInteractor
import ru.divizdev.homefinance.presentation.operation.view.OperationUI
import java.math.BigDecimal

class OperationPresenter(private val repositoryWallet: RepositoryWallet,
                         private val repositoryCategory: RepositoryCategory,
                         private val operationInteractor: OperationInteractor) : AbstractOperationPresenter() {
    private lateinit var wallets: List<Wallet>
    private lateinit var categories: List<Category>

    override fun loadData() {
        launch {
            wallets = repositoryWallet.getAll()
            categories = repositoryCategory.getAll()

            launch(UI) {
                val categoryNames = categories.map { category ->  category.categoryName}
                val walletNames = wallets.map { wallet -> wallet.walletName}
                weakReferenceView.get()?.onLoadData(categoryNames, walletNames) }
        }
    }

    override fun save() {

        val view = weakReferenceView.get()
        val operationUI = view?.getOperation()
        if (operationUI?.value == null) {
            view?.showErrorObligatoryField()
            return
        }

        operationInteractor.addOperation(convertOperationUiTOModel(operationUI))

        view.exit()
    }

    private fun convertOperationUiTOModel(operationUI: OperationUI) : Operation {
        val wallet = wallets[operationUI.walletNumber]
        val category = categories[operationUI.categoryNumber]
        val sumCurrencyOperation = Money(BigDecimal.valueOf(operationUI.value ?: 0.0), operationUI.currency)
        val date = operationUI.date
        val comment = operationUI.comment

        return Operation(comment = comment, sumCurrencyOperation = sumCurrencyOperation, date = date,
                category = category, wallet = wallet)
    }
}