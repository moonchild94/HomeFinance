package ru.divizdev.homefinance.presentation.operation.view

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import java.util.*
import android.support.design.widget.Snackbar
import android.widget.CompoundButton
import java.time.LocalDate


class OperationActivity : BaseMvpActivity<AbstractOperationPresenter, IOperationView>(), IOperationView {
    override fun onLoadCategories(categories: List<String>) {
        category_spinner.adapter = ArrayAdapter(category_spinner.context, android.R.layout.simple_spinner_item, categories)
    }

    override fun onLoadWallets(wallets: List<String>) {
        wallet_spinner.adapter = ArrayAdapter(wallet_spinner.context, android.R.layout.simple_spinner_item, wallets)
    }

    override fun showErrorObligatoryField() {
        Toast.makeText(this, R.string.text_error_obligatory, Toast.LENGTH_LONG).show()
    }

    override fun exit() {
        onBackPressed()
    }

    override fun getInstancePresenter(): AbstractOperationPresenter {
        return Factory.getOperationPresenter()
    }

    override fun getMvpView(): IOperationView {
        return this
    }

    override fun getOperation(): OperationUI {
        val operationType = OperationType.values()[type_operation_spinner.selectedItemPosition]
        val currency: Currency = Currency.values()[currency_spinner.selectedItemPosition]
        val date = if (periodicSwitch.isChecked ) truncTimeFromDate(datePickerInputEditText.date) else datePickerInputEditText.date.time
        return OperationUI(date,
                timePickerInputEditText.time.time,
                operationType,
                category_spinner.selectedItemPosition,
                wallet_spinner.selectedItemPosition,
                value_edit_text.text.toString().toDoubleOrNull(),
                currency,
                comment_edit_text.text.toString(),
                period_in_days.text.toString().toIntOrNull() ?: 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.loadWallets()

        initOperationTypeSpinner()
        initSwitchPeriodicType()

        val currencies = Currency.values().map { currency -> currency.sign }
        currency_spinner.adapter = ArrayAdapter(currency_spinner.context, android.R.layout.simple_spinner_item, currencies)

        datePickerInputEditText.manager = supportFragmentManager
        timePickerInputEditText.manager = supportFragmentManager
        datePickerInputEditText.setDateFormat(DateFormat.getMediumDateFormat(applicationContext))
        timePickerInputEditText.setTimeFormat(DateFormat.getTimeFormat(applicationContext))

        datePickerInputEditText.date = Calendar.getInstance()
        datePickerInputEditText.setText(DateFormat.getDateFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.setText(DateFormat.getTimeFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.time = Calendar.getInstance()

        value_edit_text.requestFocus()

        save_button.setOnClickListener { presenter.save() }
    }

    private fun initSwitchPeriodicType() {
        periodicSwitch.setOnCheckedChangeListener { _, _ ->
             periodicSwitcher.showNext()
        }
    }

    private fun truncTimeFromDate(calendar: Calendar) : Date {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return Date(calendar.timeInMillis)
    }

    private fun initOperationTypeSpinner() {
        val operationTypes = OperationType.values().map { type -> getString(type.stringId) }
        type_operation_spinner.adapter = ArrayAdapter(type_operation_spinner.context, android.R.layout.simple_spinner_item, operationTypes)

        type_operation_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.loadCategories(OperationType.values()[position])
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
