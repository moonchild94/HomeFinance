package ru.divizdev.homefinance.presentation.transaction.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import kotlinx.android.synthetic.main.activity_transaction.*
import ru.divizdev.homefinance.R
import java.util.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

//        datePickerEditText.manager = getSupportFragmentManager();
//        timePickerEditText.manager = getSupportFragmentManager();

        datePickerInputEditText.manager = supportFragmentManager
        timePickerInputEditText.manager = supportFragmentManager



        datePickerInputEditText.setDateFormat(DateFormat.getMediumDateFormat(applicationContext))
        timePickerInputEditText.setTimeFormat(DateFormat.getTimeFormat(applicationContext))

        datePickerInputEditText.setDate(Calendar.getInstance())
        datePickerInputEditText.setText(DateFormat.getDateFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.setText(DateFormat.getTimeFormat(applicationContext).format(Calendar.getInstance().time))
        timePickerInputEditText.setTime(Calendar.getInstance())




    }
}
