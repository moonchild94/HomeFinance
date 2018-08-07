package ru.divizdev.homefinance.presentation.operationslist.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_list_operation.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.operationslist.adapter.OperationListAdapter

class OperationListFragment : BaseMvpFragment<AbstractOperationListPresenter, IOperationListView>(), IOperationListView {
    private lateinit var parentView: IMainView
    private lateinit var operationListAdapter: OperationListAdapter

    override fun showDeleteFragmentDialog(position: Int) {
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_operation_confirmation))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    presenter.onDeleteOperation(position)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
                .show()
    }

    override fun showWalletsSpinner(wallets: List<String>) {
        val walletsWithAllElement = wallets.toMutableList()
        walletsWithAllElement.add(0, getString(R.string.all))
        filter_wallet_spinner.adapter = ArrayAdapter<String>(filter_wallet_spinner.context, android.R.layout.simple_spinner_item, walletsWithAllElement)
    }

    override fun getInstancePresenter(): AbstractOperationListPresenter {
        return Factory.getOperationListPresenter()
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

        presenter.loadWallets()

        list_transaction_recylcer_view.layoutManager = LinearLayoutManager(context)
        operationListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils())
        { position: Int -> showDeleteFragmentDialog(position) }
        list_transaction_recylcer_view.adapter = operationListAdapter

        initFilters()

        add_operation.setOnClickListener { parentView.openAddOperation() }

        periodic_operations_switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.loadOperations(filter_wallet_spinner.selectedItemPosition, isChecked)
        }
    }

    override fun showOperationList(operationList: List<Operation>) {
        operationListAdapter.setData(operationList)
    }

    private fun initFilters() {
        filter_wallet_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                presenter.loadOperations(position, periodic_operations_switch.isChecked)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}
