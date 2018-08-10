package ru.divizdev.homefinance.presentation.operation.view

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_operation.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractAddOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import java.util.*

private const val TEMPLATE_KEY = "template"

class AddOperationFragment : BaseMvpFragment<AbstractAddOperationPresenter, IAddOperationView,
        IOperationView, AbstractOperationPresenter>(), IAddOperationView {

    companion object {
        fun newInstance(template: Operation?): AddOperationFragment {
            return AddOperationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEMPLATE_KEY, template)
                }
            }
        }
    }

    override fun onLoadCategories(categories: List<String>) {
        category_spinner.adapter = ArrayAdapter(category_spinner.context, android.R.layout.simple_spinner_dropdown_item, categories)
    }

    override fun onLoadWallets(wallets: List<String>) {
        wallet_spinner.adapter = ArrayAdapter(wallet_spinner.context, android.R.layout.simple_spinner_dropdown_item, wallets)
    }

    override fun showErrorObligatoryField() {
        Toast.makeText(requireContext(), ru.divizdev.homefinance.R.string.text_error_obligatory, Toast.LENGTH_LONG).show()
    }

    override fun exit() {
        requireActivity().onBackPressed()
    }

    override fun getInstancePresenter(): AbstractAddOperationPresenter {
        return Factory.getAddOperationPresenter((requireContext()
                as BaseMvpActivity<AbstractOperationPresenter, IOperationView>).presenter)
    }

    override fun getMvpView(): IAddOperationView {
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return layoutInflater.inflate(R.layout.fragment_add_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadWallets()

        initCategoryTypeSpinner()
        initSwitchPeriodicType()

        val currencies = Currency.values().map { currency -> currency.sign }
        currency_spinner.adapter = ArrayAdapter(currency_spinner.context, android.R.layout.simple_spinner_dropdown_item, currencies)

        datePickerInputEditText.manager = fragmentManager
        timePickerInputEditText.manager = fragmentManager
        datePickerInputEditText.setDateFormat(DateFormat.getDateFormat(requireContext()))
        timePickerInputEditText.setTimeFormat(DateFormat.getTimeFormat(requireContext()))

        datePickerInputEditText.date = Calendar.getInstance()
        datePickerInputEditText.setText(DateFormat.getDateFormat(requireContext()).format(Calendar.getInstance().time))
        timePickerInputEditText.setText(DateFormat.getTimeFormat(requireContext()).format(Calendar.getInstance().time))
        timePickerInputEditText.time = Calendar.getInstance()

        value_edit_text.requestFocus()

        save_button.setOnClickListener { presenter.save() }
        save_template_button.setOnClickListener { presenter.saveTemplate() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.templates -> {
                Factory.getRouter().navToTemplates(requireActivity())
                return true
            }
            else -> IllegalAccessException()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter.initializeByTemplate(arguments?.getParcelable(TEMPLATE_KEY))
    }

    override fun getOperation(): OperationUI {
        val operationType = CategoryType.values()[type_category_spinner.selectedItemPosition]
        val currency: Currency = Currency.values()[currency_spinner.selectedItemPosition]
        val date = if (periodicSwitch.isChecked) truncTimeFromDate(datePickerInputEditText.date) else datePickerInputEditText.date.time
        return OperationUI(date,
                timePickerInputEditText.time.time,
                operationType,
                category_spinner.selectedItemPosition,
                wallet_spinner.selectedItemPosition,
                value_edit_text.text.toString().toDoubleOrNull(),
                currency,
                comment_edit_text.text.toString(),
                period_in_days.text.toString().toIntOrNull() ?: 0,
                if (periodicSwitch.isChecked) OperationType.PERIODIC else OperationType.COMPLETE)
    }

    override fun initializeByTemplate(template: OperationUI) {
        type_category_spinner.setSelection(template.categoryType.ordinal)
        category_spinner.setSelection(template.categoryNumber)
        comment_edit_text.setText(template.comment)
        currency_spinner.setSelection(template.currency.ordinal)
        wallet_spinner.setSelection(template.walletNumber)
        value_edit_text.setText(template.value.toString())
        periodicSwitch.isChecked = template.period != 0
        if (template.period != 0) {
            period_in_days.setText(template.period.toString())
        }
    }

    private fun initSwitchPeriodicType() {
        periodicSwitch.setOnCheckedChangeListener { _, _ ->
            periodicSwitcher.showNext()
        }
    }

    private fun truncTimeFromDate(calendar: Calendar): Date {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return Date(calendar.timeInMillis)
    }

    private fun initCategoryTypeSpinner() {
        val operationTypes = CategoryType.values().map { type -> getString(type.stringId) }
        type_category_spinner.adapter = ArrayAdapter(type_category_spinner.context, android.R.layout.simple_spinner_dropdown_item, operationTypes)

        type_category_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.loadCategories(CategoryType.values()[position])
            }
        }
    }
}
