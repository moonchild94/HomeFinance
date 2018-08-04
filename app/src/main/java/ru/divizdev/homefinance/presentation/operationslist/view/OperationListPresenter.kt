package ru.divizdev.homefinance.presentation.operationslist.view

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.data.repository.RepositoryWallet
import ru.divizdev.homefinance.data.repository.RepositoryWalletOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.Wallet

class OperationListPresenter(private val repositoryOperation: RepositoryOperation,
                             private val repositoryWallet: RepositoryWallet,
                             private val repositoryWalletOperation: RepositoryWalletOperation) : AbstractOperationListPresenter() {
    private lateinit var operations: List<Operation>
    private lateinit var wallets: List<Wallet>
    private var selectedWalletPosition = 0

    override fun onDeleteOperation(position: Int) {
        launch {
            repositoryWalletOperation.delete(operations[position])
        }.invokeOnCompletion { loadOperations(selectedWalletPosition) }
    }

    override fun loadWallets() {
        launch {
            wallets = repositoryWallet.getAll()
            val walletNames = wallets.map { wallet -> wallet.walletName }.toMutableList()
            launch(UI) { weakReferenceView.get()?.showWalletsSpinner(walletNames) }
        }
    }

    override fun loadOperations(position: Int) {
        launch {
            selectedWalletPosition = position
            operations = if (selectedWalletPosition == 0) repositoryOperation.getAll() else repositoryOperation.query(wallets[selectedWalletPosition - 1])
            launch(UI) { weakReferenceView.get()?.showOperationList(operations) }
        }
    }
}