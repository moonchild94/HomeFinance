package ru.divizdev.homefinance.presentation.listTransaction.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_list_transaction.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.listTransaction.adapter.OperationListAdapter

class OperationListFragment : BaseMvpFragment<AbstractOperationListPresenter, IOperationListView>(), IOperationListView {
    override fun showDeleteFragmentDialog(position: Int) {
        AlertDialog.Builder(requireContext())
                .setTitle("Вы действительно хотите удалить операцию?")
                .setPositiveButton("OK") { _, _ ->
                    presenter.onDeleteOperation(position)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
                .show()
    }

    override fun showWalletsSpinner(wallets: List<String>) {
        filter_wallet_spinner.adapter = ArrayAdapter<String>(filter_wallet_spinner.context, android.R.layout.simple_spinner_item, wallets)
    }

    private lateinit var operationListAdapter: OperationListAdapter

    override fun getInstancePresenter(): AbstractOperationListPresenter {
        return Factory.getOperationListPresenter()
    }

    override fun getMvpView(): IOperationListView {
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadWallets()

        list_transaction_recylcer_view.layoutManager = LinearLayoutManager(context)
        operationListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils())
        { position: Int -> showDeleteFragmentDialog(position) }
        list_transaction_recylcer_view.adapter = operationListAdapter

        initFilters()
    }

    override fun onStart() {
        super.onStart()
        presenter.loadOperations(0)
    }

    override fun showOperationList(operationList: List<Operation>) {
        operationListAdapter.setData(operationList)
    }

    private fun initFilters() {
        filter_wallet_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                presenter.loadOperations(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}
