package ru.divizdev.homefinance.presentation.operationslist.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_list_operation.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.operationslist.adapter.OperationListAdapter
import ru.divizdev.homefinance.presentation.operationslist.presenter.AbstractOperationListPresenter

private const val SELECTED_ITEM_POS_KEY = "selectedItemPosition"

class OperationListFragment : BaseMvpFragment<AbstractOperationListPresenter, IOperationListView, IMainView, AbstractMainPresenter>(), IOperationListView {
    private lateinit var parentView: IMainView
    private lateinit var operationListAdapter: OperationListAdapter
    private lateinit var walletListAdapter: ArrayAdapter<String>

    private var selectedWalletPosition = -1

    override fun showDeleteFragmentDialog(position: Int) {
        DeleteOperationDialogFragment.newInstance(presenter.getOperation(position))
                .show(childFragmentManager, null)
    }

    override fun showWalletsSpinner(wallets: List<String>) {
        val walletsWithAllElement = wallets.toMutableList()
        walletsWithAllElement.add(0, getString(R.string.all))
        walletListAdapter.clear()
        walletListAdapter.addAll(walletsWithAllElement)

        if (selectedWalletPosition >= 0) {
            filter_wallet_spinner.setSelection(selectedWalletPosition)
            selectedWalletPosition = -1
        }
    }

    override fun getInstancePresenter(): AbstractOperationListPresenter {
        return Factory.getOperationListPresenter((requireContext()
                as BaseMvpActivity<AbstractMainPresenter, IMainView>).presenter)
    }

    override fun getMvpView(): IOperationListView {
        return this
    }

    override fun onAttach(context: Context?) {
        if (context !is IMainView) {
            throw IllegalArgumentException() // todo переделать по-людски
        }
        parentView = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedWalletPosition = savedInstanceState?.getInt(SELECTED_ITEM_POS_KEY) ?: 0
        presenter.loadWallets()

        list_transaction_recylcer_view.layoutManager = LinearLayoutManager(context)
        operationListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils(),
                { position: Int -> showDeleteFragmentDialog(position) }, {})
        list_transaction_recylcer_view.adapter = operationListAdapter

        initFilters()

        add_operation.setOnClickListener { parentView.openAddOperation() }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_POS_KEY, filter_wallet_spinner.selectedItemPosition)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showOperationList(operationList: List<Operation>) {
        operationListAdapter.setData(operationList)
    }

    private fun initFilters() {
        walletListAdapter = ArrayAdapter(filter_wallet_spinner.context, android.R.layout.simple_spinner_dropdown_item, mutableListOf())
        filter_wallet_spinner.adapter = walletListAdapter
        filter_wallet_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                presenter.loadOperations(position, periodic_operations_switch.isChecked)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        periodic_operations_switch.setOnCheckedChangeListener { _, isChecked ->
            if (filter_wallet_spinner.selectedItemPosition >= 0) {
                presenter.loadOperations(filter_wallet_spinner.selectedItemPosition, isChecked)
            }
        }
    }
}
