package ru.divizdev.homefinance.presentation.wallets.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_edit_wallet.view.*
import kotlinx.android.synthetic.main.fragment_wallets.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.wallets.adapter.WalletListAdapter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractWalletsPresenter

class WalletsFragment : BaseMvpFragment<AbstractWalletsPresenter, IWalletsView>(), IWalletsView {
    private lateinit var walletListAdapter: WalletListAdapter
    private lateinit var parentView: IMainView

    override fun showDeleteFragmentDialog(position: Int) {
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_wallet_confirmation)
                .setPositiveButton(R.string.ok) { _, _ ->
                    presenter.onDeleteOperation(position)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()
                .show()
    }

    override fun showEditFragmentDialog(position: Int) {
        val editWalletDialogView = layoutInflater.inflate(R.layout.dialog_edit_wallet, null)
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.edit_wallet_title)
                .setView(editWalletDialogView)
                .setPositiveButton(R.string.ok) { _, _ ->
                    presenter.onEditOperation(position, editWalletDialogView.wallet_name.text.toString())
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()
                .show()
    }

    override fun getInstancePresenter(): AbstractWalletsPresenter {
        return Factory.getWalletsPresenter()
    }

    override fun getMvpView(): IWalletsView {
        return this
    }

    override fun setListWallets(wallets: List<Wallet>) {
        walletListAdapter.updateData(wallets)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onAttach(context: Context?) {
        if (context !is IMainView) {
            throw IllegalArgumentException() // todo переделать по-людски
        }
        parentView = context
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(view.context)
        wallets_recycler_view.layoutManager = linearLayoutManager
        walletListAdapter = WalletListAdapter(listOf(), Factory.getLocaleUtils(),
                { showDeleteFragmentDialog(it) }, { showEditFragmentDialog(it) })
        wallets_recycler_view.adapter = walletListAdapter

        add_wallet.setOnClickListener {
            AddWalletDialogFragment.newInstance().show(childFragmentManager, null)
        }

        presenter.loadData()

        super.onViewCreated(view, savedInstanceState)
    }
}
