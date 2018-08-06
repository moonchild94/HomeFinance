package ru.divizdev.homefinance.presentation.operation.presenter

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryCategory
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.model.OperationInteractor
import ru.divizdev.homefinance.presentation.operation.view.OperationUI
import java.math.BigDecimal

class OperationPresenter(private val repositoryWallet: RepositoryWallet,
                         private val repositoryCategory: RepositoryCategory,
                         private val operationInteractor: OperationInteractor) : AbstractOperationPresenter() {
    private lateinit var wallets: List<Wallet>
    private lateinit var categories: List<Category>

    override fun loadWallets() {
        repositoryWallet.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    val walletNames = wallets.map { wallet -> wallet.walletName }
                    weakReferenceView.get()?.onLoadWallets(walletNames)
                }
    }

    override fun loadCategories(operationType: OperationType) {
        repositoryCategory.query(operationType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    categories = it
                    val categoryNames = categories.map { category -> category.categoryName }
                    weakReferenceView.get()?.onLoadCategories(categoryNames)
                }
    }

    override fun save() {

        val view = weakReferenceView.get()
        val operationUI = view?.getOperation()
        if (operationUI?.value == null) {
            view?.showErrorObligatoryField()
            return
        }

        Completable.fromAction { operationInteractor.addOperation(convertOperationUiTOModel(operationUI)) }
                .subscribeOn(Schedulers.io())
                .subscribe {}

        view.exit()
    }

    private fun convertOperationUiTOModel(operationUI: OperationUI): Operation {
        val wallet = wallets[operationUI.walletNumber]
        val category = categories[operationUI.categoryNumber]
        val sumCurrencyOperation = Money(BigDecimal.valueOf(operationUI.value
                ?: 0.0), operationUI.currency)
        val date = operationUI.date
        val comment = operationUI.comment
        val period = operationUI.period

        return Operation(comment = comment, sumCurrencyOperation = sumCurrencyOperation, date = date,
                category = category, wallet = wallet, periodic = period != 0, period = period)
    }
}