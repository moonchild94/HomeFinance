package ru.divizdev.homefinance.presentation.operationslist.view

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.model.OperationInteractor

class OperationListPresenter(private val operationInteractor: OperationInteractor,
                             private val repositoryWallet: RepositoryWallet,
                             private val repositoryOperation: RepositoryOperation) : AbstractOperationListPresenter() {
    private lateinit var operations: List<Operation>
    private lateinit var wallets: List<Wallet>
    private var selectedWalletPosition = 0

    override fun onDeleteOperation(position: Int) {
        Completable.fromAction { repositoryOperation.delete(operations[position]) }
                .subscribeOn(Schedulers.io())
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
        Completable.fromAction {
            selectedWalletPosition = position
            val flowableOperations = if (selectedWalletPosition == 0) operationInteractor.getAllOperations(isPeriodic)
            else operationInteractor.queryOperationsByWallet(wallets[selectedWalletPosition - 1], isPeriodic)
            flowableOperations
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        operations = it
                        weakReferenceView.get()?.showOperationList(operations)
                    }
        }.subscribeOn(Schedulers.io()).subscribe {}
    }
}