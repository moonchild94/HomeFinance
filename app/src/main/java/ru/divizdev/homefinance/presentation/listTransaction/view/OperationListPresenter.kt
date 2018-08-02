package ru.divizdev.homefinance.presentation.listTransaction.view

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.repository.RepositoryOperation

class OperationListPresenter(private val repositoryOperation: RepositoryOperation) : AbstractOperationListPresenter() {
    override fun loadOperations() {
        launch {
            val operations = repositoryOperation.getAllOperations()
            launch(UI) { weakReferenceView.get()?.showOperationList(operations) }
        }
    }
}