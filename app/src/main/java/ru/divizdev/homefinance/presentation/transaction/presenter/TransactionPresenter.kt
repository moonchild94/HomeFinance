package ru.divizdev.homefinance.presentation.transaction.presenter

import ru.divizdev.homefinance.model.UserWalletManager

class TransactionPresenter(private val userWalletManager: UserWalletManager) : AbstractTransactionPresenter() {
    override fun save() {

        val view = weakReferenceView.get()
        val transaction = view?.getTransaction()
        if (transaction?.value == null) {
            view?.showErrorObligatoryField()
            return
        }

        userWalletManager.addTransaction(transaction.walletKey, transaction)

        view?.exit()

    }


}