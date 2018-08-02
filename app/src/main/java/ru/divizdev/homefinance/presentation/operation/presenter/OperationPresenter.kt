package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.model.UserWalletManager

class OperationPresenter(private val userWalletManager: UserWalletManager) : AbstractOperationPresenter() {
    override fun save() {

        val view = weakReferenceView.get()
        val operation = view?.getOperation()
        if (operation?.value == null) {
            view?.showErrorObligatoryField()
            return
        }

        userWalletManager.addOperation(operation.walletKey, operation)

        view.exit()

    }


}