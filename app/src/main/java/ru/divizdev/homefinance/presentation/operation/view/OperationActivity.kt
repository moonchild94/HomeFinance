package ru.divizdev.homefinance.presentation.operation.view

import android.os.Bundle
import android.text.format.DateFormat
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

class OperationActivity : BaseMvpActivity<AbstractOperationPresenter, IOperationView>(), IOperationView {
    override fun onLoadData(categories: List<String>, wallets: List<String>) {
        category_spinner.adapter = ArrayAdapter(category_spinner.context, android.R.layout.simple_spinner_item, categories)
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
        return OperationUI(datePickerInputEditText.date.time,
                timePickerInputEditText.time.time,
                operationType,
                category_spinner.selectedItemPosition,
                wallet_spinner.selectedItemPosition,
                value_edit_text.text.toString().toDoubleOrNull(),
                currency,
                comment_edit_text.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.loadData()

        val operationTypes = OperationType.values().map { type -> getString(type.stringId) }
        type_operation_spinner.adapter = ArrayAdapter(type_operation_spinner.context, android.R.layout.simple_spinner_item, operationTypes)

        val currencies = Currency.values().map { currency ->  currency.sign}
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
