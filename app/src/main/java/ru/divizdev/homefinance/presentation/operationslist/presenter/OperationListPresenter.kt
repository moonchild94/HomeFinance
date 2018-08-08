package ru.divizdev.homefinance.presentation.operationslist.presenter

import android.util.Log
import io.reactivex.Completable
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
                             private val repositoryOperation: RepositoryOperation,
                             parentPresenter: AbstractMainPresenter) : AbstractOperationListPresenter(parentPresenter) {
    private val subscription: Subject<OperationFilter> = BehaviorSubject.create()
    private lateinit var operations: List<Operation>
    private lateinit var wallets: List<Wallet>

    override fun onDeleteOperation(position: Int) {
        Completable.fromAction { repositoryOperation.delete(operations[position]) }
                .subscribeOn(Schedulers.io())
                .doOnError { th -> Log.e(this.javaClass.simpleName, th.message) }
                .subscribe {}
    }

    override fun loadWallets() {
        repositoryWallet.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    wallets = it
                    weakReferenceView.get()?.showWalletsSpinner(wallets.map { wallet -> wallet.walletName })
                }
    }

    override fun loadOperations(position: Int, isPeriodic: Boolean) {
        Log.e(this.javaClass.simpleName, "$isPeriodic $position")
        subscription.onNext(OperationFilter(position, if (isPeriodic) OperationType.PERIODIC else OperationType.COMPLETE))
    }

    override fun attach() {
        Log.e(this.javaClass.simpleName, "attach")
        subscription
                .observeOn(Schedulers.io())
                .flatMap { filter: OperationFilter ->
                    Log.e(this.javaClass.simpleName, filter.toString())
                    val wallet = if (filter.walletPosition == 0) null else wallets[filter.walletPosition - 1]
                    operationInteractor.queryOperationsByWallet(wallet, filter.operationType)
                            .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(this.javaClass.simpleName, it.toString())
                    operations = it
                    weakReferenceView.get()?.showOperationList(operations)
                }
    }

    override fun detach() {
        subscription.unsubscribeOn(Schedulers.io())
    }
}