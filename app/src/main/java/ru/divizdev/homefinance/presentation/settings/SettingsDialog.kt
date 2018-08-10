package ru.divizdev.homefinance.presentation.settings

import android.app.Dialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Currency

private const val KEY_NAME_PREF = "pref_currency"

class SettingsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val currencyDefaultValue = context!!.getString(R.string.pref_currency_default_value)
        val stringArray = Currency.values().map { it.sign }.toTypedArray()

        val currentValue = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_NAME_PREF, currencyDefaultValue)
        var index = 0
        for (i in stringArray.indices) {
            if (stringArray[i].compareTo(currentValue!!) == 0) {
                index = i
                break
            }
        }

        return AlertDialog.Builder(activity!!)
                .setTitle(R.string.pref_name_currency)
                .setSingleChoiceItems(stringArray, index) { dialogInterface, i ->
                    android.preference.PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_NAME_PREF, stringArray[i]).apply()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->

                    dialogInterface.dismiss()
                }
                .create()
    }


}
