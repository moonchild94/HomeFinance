package ru.divizdev.homefinance.presentation.wallets.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpDialogFragment
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractDeleteWalletPresenter

const val DELETING_WALLET_KEY = "deletingWalletKey"

class DeleteWalletDialogFragment : BaseMvpDialogFragment<AbstractDeleteWalletPresenter, IDeleteWalletView>(), IDeleteWalletView {

    override fun getMvpView(): IDeleteWalletView {
        return this
    }

    override fun getInstancePresenter(): AbstractDeleteWalletPresenter {
        return Factory.getDeleteWalletPresenter()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val wallet = arguments?.getParcelable<Wallet>(DELETING_WALLET_KEY)
        return AlertDialog.Builder(context)
                .setTitle(getString(R.string.delete_wallet_confirmation))
                .setPositiveButton(getString(R.string.ok)) { _, _ -> if (wallet != null) presenter.onDeleteWallet(wallet) }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
    }

    companion object {
        @JvmStatic
        fun newInstance(wallet: Wallet): DeleteWalletDialogFragment {
            return DeleteWalletDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DELETING_WALLET_KEY, wallet)
                }
            }
        }
    }
}