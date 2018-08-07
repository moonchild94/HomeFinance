package ru.divizdev.homefinance.presentation.operation.view

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_operation.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractAddOperationPresenter
import java.util.*

class AddOperationFragment : BaseMvpFragment<AbstractAddOperationPresenter, IAddOperationView>(), IAddOperationView {
    override fun onLoadCategories(categories: List<String>) {
        category_spinner.adapter = ArrayAdapter(category_spinner.context, android.R.layout.simple_spinner_item, categories)
    }

    override fun onLoadWallets(wallets: List<String>) {
        wallet_spinner.adapter = ArrayAdapter(wallet_spinner.context, android.R.layout.simple_spinner_item, wallets)
    }

    override fun showErrorObligatoryField() {
        Toast.makeText(requireContext(), ru.divizdev.homefinance.R.string.text_error_obligatory, Toast.LENGTH_LONG).show()
    }

    override fun exit() {
        requireActivity().onBackPressed()
    }

    override fun getInstancePresenter(): AbstractAddOperationPresenter {
        return Factory.getAddOperationPresenter()
    }

    override fun getMvpView(): IAddOperationView {
        return this
    }

    override fun getOperation(): OperationUI {
        val operationType = CategoryType.values()[type_operation_spinner.selectedItemPosition]
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_add_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadWallets()

        open_templates.setOnClickListener {Factory.getRouter().navToTemplates(requireActivity()) }

        initOperationTypeSpinner()
        initSwitchPeriodicType()

        val currencies = Currency.values().map { currency -> currency.sign }
        currency_spinner.adapter = ArrayAdapter(currency_spinner.context, android.R.layout.simple_spinner_item, currencies)

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

    private fun initOperationTypeSpinner() {
        val operationTypes = CategoryType.values().map { type -> getString(type.stringId) }
        type_operation_spinner.adapter = ArrayAdapter(type_operation_spinner.context, android.R.layout.simple_spinner_item, operationTypes)

        type_operation_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.loadCategories(CategoryType.values()[position])
            }
        }
    }
}
