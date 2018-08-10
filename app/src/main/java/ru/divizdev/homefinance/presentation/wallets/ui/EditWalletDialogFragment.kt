package ru.divizdev.homefinance.presentation.wallets.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_edit_wallet.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpDialogFragment
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractEditWalletPresenter

const val EDIT_WALLET_KEY = "editingWalletKey"

class EditWalletDialogFragment : BaseMvpDialogFragment<AbstractEditWalletPresenter, IEditWalletView>(), IEditWalletView {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val wallet = arguments?.getParcelable<Wallet>(EDIT_WALLET_KEY)
        val editWalletDialogView = requireActivity().layoutInflater.inflate(R.layout.dialog_edit_wallet, null)
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.edit_wallet_title)
                .setView(editWalletDialogView)
                .setPositiveButton(R.string.ok) { _, _ ->
                    if (wallet != null) {
                        presenter.onEditWallet(wallet, editWalletDialogView.wallet_name_edit_text.text.toString())
                    }
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()
    }

    override fun getInstancePresenter(): AbstractEditWalletPresenter {
        return Factory.getEditWalletPresenter()
    }

    override fun getMvpView(): IEditWalletView {
        return this
    }

    companion object {
        @JvmStatic
        fun newInstance(wallet: Wallet): EditWalletDialogFragment {
            return EditWalletDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EDIT_WALLET_KEY, wallet)
                }
            }
        }
    }
}