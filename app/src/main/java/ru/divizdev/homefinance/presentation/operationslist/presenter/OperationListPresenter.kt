package ru.divizdev.homefinance.presentation.operationslist.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.model.OperationInteractor
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter

class OperationListPresenter(private val operationInteractor: OperationInteractor,
                             private val repositoryWallet: RepositoryWallet,
                             parentPresenter: AbstractMainPresenter) : AbstractOperationListPresenter(parentPresenter) {
    private val subscription: Subject<OperationFilter> = BehaviorSubject.create()
    private lateinit var operations: List<Operation>
    private lateinit var wallets: List<Wallet>

    override fun loadWallets() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.showWalletsSpinner(wallets.map { wallet -> wallet.walletName })
                }
    }

    override fun loadOperations(position: Int, isPeriodic: Boolean) {
        subscription.onNext(OperationFilter(position, if (isPeriodic) OperationType.PERIODIC else OperationType.COMPLETE))
    }

    override fun getOperation(position: Int) : Operation {
        return operations[position]
    }

    override fun attach() {
        subscription
                .observeOn(Schedulers.io())
                .switchMap { filter: OperationFilter ->
                    while (!::wallets.isInitialized) { }
                    val wallet = if (filter.walletPosition == 0) null else wallets[filter.walletPosition - 1]
                    operationInteractor.queryOperationsByWallet(wallet, filter.operationType)
                            .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    operations = it
                    weakReferenceView.get()?.showOperationList(operations)
                }
    }

    override fun detach() {
        subscription.unsubscribeOn(Schedulers.io())
    }
}