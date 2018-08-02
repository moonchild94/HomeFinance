package ru.divizdev.homefinance.presentation.listTransaction.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_operation.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.presentation.LocaleUtils

class OperationListAdapter(private var listOperations: List<Operation>, private val localeUtils: LocaleUtils) : RecyclerView.Adapter<OperationListAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_operation, parent, false)
        return ViewHolder(view, localeUtils)
    }

    override fun getItemCount(): Int {
        return listOperations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listOperations[position])
    }

    fun setData(newListOperation: List<Operation>) {
        listOperations = newListOperation
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, private val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view) {

        private var operation: Operation? = null

        fun setData(operation: Operation) {
            this.operation = operation
            setMoney(operation.sumCurrencyMain, itemView.transaction_balance_text_view, itemView.transaction_currency_balance_text_view)
        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.amount)
            currency.text = localeUtils.formatCurrency(money.currency)
        }
    }
}