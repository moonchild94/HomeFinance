package ru.divizdev.homefinance.presentation.wallets.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.dialog_add_wallet.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpDialogFragment
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractAddWalletPresenter
import java.math.BigDecimal

/**
 * Диалог, который отображается при создании счета.
 */
class AddWalletDialogFragment : BaseMvpDialogFragment<AbstractAddWalletPresenter, IAddWalletView>(), IAddWalletView {

    private val dialogView by lazy { activity?.layoutInflater?.inflate(R.layout.dialog_add_wallet, null) }
    private val dialog by lazy {
        AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle(getString(R.string.adding_wallet_title))
                .setPositiveButton(getString(R.string.ok)) { _, _ -> onAddWallet() }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogView?.wallet_currency_spinner?.adapter = ArrayAdapter<Currency>(context, android.R.layout.simple_spinner_item, Currency.values())

        dialogView?.wallet_name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = text != null && text.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = dialogView?.wallet_name?.text?.isNotEmpty() ?: false
    }

    override fun getInstancePresenter(): AbstractAddWalletPresenter {
        return Factory.getAddWalletPresenter()
    }

    override fun getMvpView(): IAddWalletView {
        return this
    }

    private fun onAddWallet() {
        val walletName = dialogView?.wallet_name?.text.toString()
        val startAmount = dialogView?.wallet_start_amount?.text.toString()
        val currency = dialogView?.wallet_currency_spinner?.selectedItem as Currency
        val balance = Money(if (startAmount.isEmpty()) BigDecimal.valueOf(0) else startAmount.toBigDecimal(), currency)
        val wallet = Wallet(walletName = walletName, balance = balance)
        presenter.onAddWallet(wallet)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddWalletDialogFragment()
    }
}