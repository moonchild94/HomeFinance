package ru.divizdev.homefinance.presentation.settings

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Currency

const val KEY_NAME_PREF = "pref_currency"

class SettingsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val currencyDefaultValue = context!!.getString(R.string.pref_currency_default_value)
        val currentValue = Factory.getSharedPreference().getString(KEY_NAME_PREF, currencyDefaultValue)
        val index = Currency.values().indexOf(Currency.valueOf(currentValue))

        val stringArray = Currency.values().map { it.sign }.toTypedArray()

        return AlertDialog.Builder(activity!!)
                .setTitle(R.string.pref_name_currency)
                .setSingleChoiceItems(stringArray, index) { dialogInterface, i ->
                    Factory.getSharedPreference().edit().putString(KEY_NAME_PREF,
                            Currency.values().find { it.sign == stringArray[i] }?.name
                                    ?: Currency.RUB.name).apply()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
    }


}
