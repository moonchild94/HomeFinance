package ru.divizdev.homefinance.presentation.template.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_template_list.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import ru.divizdev.homefinance.presentation.operation.view.IOperationView
import ru.divizdev.homefinance.presentation.operationslist.adapter.OperationListAdapter
import ru.divizdev.homefinance.presentation.template.presenter.AbstractTemplateListPresenter
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class TemplateListFragment : BaseMvpFragment<AbstractTemplateListPresenter, ITemplateListView,
        IOperationView, AbstractOperationPresenter>(), ITemplateListView {

    private lateinit var templateListAdapter: OperationListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_template_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.templates).isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        templates_recycle_view.layoutManager = LinearLayoutManager(context)
        templateListAdapter = OperationListAdapter(listOf(), Factory.getLocaleUtils(),
                { position: Int -> showDeleteFragmentDialog(position) },
                { position: Int -> presenter.selectTemplate(position) })
        templates_recycle_view.adapter = templateListAdapter
        presenter.loadTemplates()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    private fun showDeleteFragmentDialog(position: Int) {
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_template_confirmation))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    presenter.onDeleteOperation(position)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
                .show()
    }

    override fun showTemplates(templates: List<Operation>) {
        templateListAdapter.setData(templates)
    }

    override fun getInstancePresenter(): AbstractTemplateListPresenter {
        return Factory.getTemplateListPresenter((requireContext()
                as BaseMvpActivity<AbstractOperationPresenter, IOperationView>).presenter)
    }

    override fun getMvpView(): ITemplateListView {
        return this
    }
}