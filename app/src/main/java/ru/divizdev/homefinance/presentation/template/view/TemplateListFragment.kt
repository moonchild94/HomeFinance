package ru.divizdev.homefinance.presentation.template.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_template_list.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.operationslist.adapter.OperationListAdapter
import ru.divizdev.homefinance.presentation.template.presenter.AbstractTemplateListPresenter

class TemplateListFragment : BaseMvpFragment<AbstractTemplateListPresenter, ITemplateListView>(), ITemplateListView {

    private lateinit var templateListAdapter: OperationListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_template_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        templates_recycle_view.layoutManager = LinearLayoutManager(context)
        templateListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils()) {}
        templates_recycle_view.adapter = templateListAdapter
        presenter.loadTemplates()
    }

    override fun showTemplates(templates: List<Operation>) {
        templateListAdapter.setData(templates)
    }

    override fun getInstancePresenter(): AbstractTemplateListPresenter {
        return Factory.getTemplateListPresenter()
    }

    override fun getMvpView(): ITemplateListView {
        return this
    }
}