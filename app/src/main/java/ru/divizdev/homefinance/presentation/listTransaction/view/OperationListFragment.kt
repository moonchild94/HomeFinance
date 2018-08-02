package ru.divizdev.homefinance.presentation.listTransaction.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_transaction.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.listTransaction.adapter.OperationListAdapter

class OperationListFragment : BaseMvpFragment<AbstractOperationListPresenter, IOperationListView>(), IOperationListView {
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

        list_transaction_recylcer_view.layoutManager = LinearLayoutManager(context)
        operationListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils())
        list_transaction_recylcer_view.adapter = operationListAdapter
        presenter.loadOperations()
    }

    override fun showOperationList(operationList: List<Operation>) {
        operationListAdapter.setData(operationList)
    }
}
