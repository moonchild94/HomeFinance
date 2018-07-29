package ru.divizdev.homefinance.presentation.listTransaction.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_transaction.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Transaction
import ru.divizdev.homefinance.presentation.LocaleUtils

class ListTransactionsAdapter(val listTransactions: List<Transaction>, val localeUtils: LocaleUtils) : RecyclerView.Adapter<ListTransactionsAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction,
                parent, false)
        return ViewHolder(view, localeUtils)
    }

    override fun getItemCount(): Int {
        return listTransactions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listTransactions[position])
    }


    class ViewHolder(view: View, val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view) {

        private var transaction: Transaction? = null

        fun setData(transaction: Transaction) {
            this.transaction = transaction


            setMoney(transaction.sum, itemView.transaction_balance_text_view, itemView.transaction_currency_balance_text_view)

        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.value)
            currency.text = localeUtils.formatCurrency(money.currency)
        }
    }
}