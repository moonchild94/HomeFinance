package ru.divizdev.homefinance.presentation.operation.presenter

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryCategory
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.model.OperationInteractor
import ru.divizdev.homefinance.model.TemplateInteractor
import ru.divizdev.homefinance.presentation.operation.view.OperationUI
import java.math.BigDecimal
import java.util.*

class AddOperationPresenter(private val repositoryWallet: RepositoryWallet,
                            private val repositoryCategory: RepositoryCategory,
                            private val operationInteractor: OperationInteractor,
                            private val templateInteractor: TemplateInteractor,
                            parentPresenter: AbstractOperationPresenter) : AbstractAddOperationPresenter(parentPresenter) {

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

    override fun loadCategories(categoryType: CategoryType) {
        repositoryCategory.query(categoryType)
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

        Completable.fromAction { operationInteractor.addOperation(convertOperationUiToModel(operationUI)) }
                .subscribeOn(Schedulers.io())
                .subscribe {}

        view.exit()
    }

    override fun saveTemplate() {
        val operationUI = weakReferenceView.get()?.getOperation()
                ?: throw IllegalArgumentException()
        operationUI.operationType = OperationType.TEMPLATE
        Completable.fromAction { templateInteractor.addTemplate(convertOperationUiToModel(operationUI)) }
                .subscribeOn(Schedulers.io())
                .subscribe {}
    }

    override fun initializeByTemplate(template: Operation?) {
        if (template != null) {
            var templateUi: OperationUI? = null
            Completable.fromAction { templateUi = convertOperationUiFromModel(template) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (templateUi != null) {
                            weakReferenceView.get()?.initializeByTemplate(templateUi as OperationUI)
                        }
                    }
        }
    }

    private fun convertOperationUiToModel(operationUI: OperationUI): Operation {

        val wallet = wallets[operationUI.walletNumber]
        val category = categories[operationUI.categoryNumber]
        val sumCurrencyOperation = Money(BigDecimal.valueOf(operationUI.value
                ?: 0.0), operationUI.currency)
        val date = operationUI.date
        val comment = operationUI.comment
        val period = operationUI.period
        val operationType = operationUI.operationType

        return Operation(comment = comment, sumCurrencyOperation = sumCurrencyOperation, date = date,
                category = category, wallet = wallet, operationType = operationType, period = period)
    }

    private fun convertOperationUiFromModel(operation: Operation): OperationUI {
        val categoryType = operation.category.categoryType
        categories = repositoryCategory.query(categoryType).blockingFirst()
        if (!::wallets.isInitialized) {
            wallets = repositoryWallet.getAll().blockingFirst()
        }
        val categoryNumber = categories.indexOf(operation.category)
        val walletNumber = wallets.indexOf(operation.wallet)
        val value = operation.sumCurrencyOperation.amount.toDouble()
        val currency = operation.sumCurrencyOperation.currency
        val comment = operation.comment
        val period = operation.period
        val operationType = operation.operationType

        return OperationUI(Date(), Date(), categoryType, categoryNumber, walletNumber, value, currency,
                comment, period, operationType)
    }
}