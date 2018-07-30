package ru.divizdev.homefinance.presentation.transaction.view

import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.TypeTransaction
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.transaction.presenter.AbstractTransactionPresenter
import java.util.*

class TransactionActivity : BaseMvpActivity<AbstractTransactionPresenter, ITransactionView>(), ITransactionView {
    override fun showErrorObligatoryField() {
        Toast.makeText(this, R.string.text_error_obligatory, Toast.LENGTH_LONG).show()
    }

    override fun exit() {
        onBackPressed()
    }

    override fun getInstancePresenter(): AbstractTransactionPresenter {
        return Factory.getTransactionPresenter()
    }

    override fun getMvpView(): ITransactionView {
        return this
    }

    override fun getTransaction(): TransactionUI {
        val typeTransaction = getTypeTransaction()

        val currency: Currency = getCurrency()


        return TransactionUI(datePickerInputEditText.date.time,
                timePickerInputEditText.time.time,
                typeTransaction,
                category_spinner.getSelectedItem().toString(),
                wallet_spinner.selectedItemPosition,
                value_edit_text.text.toString().toFloatOrNull(),
                currency
                )
    }


    //Пока хардкод, в будущем нужно написать свой адаптер
    private fun getCurrency(): Currency {
        return when (currency_spinner.selectedItemPosition) {
            0 -> Currency.USD
            1 -> Currency.RUB
            else -> Currency.RUB
        }
    }

    //Пока хардкод, в будущем нужно написать свой адаптер
    private fun getTypeTransaction(): TypeTransaction {
        return when (type_operation_spinner.selectedItemPosition) {
            0 -> TypeTransaction.Expense
            1 -> TypeTransaction.Revenue
            else -> TypeTransaction.Expense
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        datePickerInputEditText.manager = supportFragmentManager
        timePickerInputEditText.manager = supportFragmentManager
        datePickerInputEditText.setDateFormat(DateFormat.getMediumDateFormat(applicationContext))
        timePickerInputEditText.setTimeFormat(DateFormat.getTimeFormat(applicationContext))

        datePickerInputEditText.setDate(Calendar.getInstance())
        datePickerInputEditText.setText(DateFormat.getDateFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.setText(DateFormat.getTimeFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.setTime(Calendar.getInstance())

        value_edit_text.requestFocus()

        save_button.setOnClickListener { presenter.save() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
