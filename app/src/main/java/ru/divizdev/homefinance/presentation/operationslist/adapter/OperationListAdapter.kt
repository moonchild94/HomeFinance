package ru.divizdev.homefinance.presentation.operationslist.adapter

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

class OperationListAdapter(private var listOperations: List<Operation>,
                           private val localeUtils: LocaleUtils,
                           private val onDeleteAction: (position: Int) -> Unit) : RecyclerView.Adapter<OperationListAdapter.ViewHolder>() {
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

    inner class ViewHolder(view: View, private val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view) {

        private var transactionCategoryName: TextView = itemView.transaction_category_name_text_view
        private var transactionCategoryComment: TextView = itemView.transaction_category_comment_text_view
        private var transactionWalletName: TextView = itemView.transaction_wallet_text_view
        private var transactionMainAmount: TextView = itemView.transaction_value_transaction_currency_text_view
        private var transactionMainCurrency: TextView = itemView.transaction_main_currency_text_view
        private var transactionCurrentAmount: TextView = itemView.transaction_balance_text_view
        private var transactionCurrentCurrency: TextView = itemView.transaction_currency_balance_text_view

        private var operation: Operation? = null

        init {
            view.setOnLongClickListener {
                onDeleteAction.invoke(adapterPosition)
                true
            }
        }

        fun setData(operation: Operation) {
            this.operation = operation
            transactionCategoryName.text = operation.category.categoryName
            transactionCategoryComment.text = operation.comment
            transactionWalletName.text = operation.wallet.walletName
            if (operation.sumCurrencyOperation.currency != operation.sumCurrencyMain.currency) {
                setMoney(operation.sumCurrencyMain, transactionMainAmount, transactionMainCurrency)
            }
            setMoney(operation.sumCurrencyOperation, transactionCurrentAmount, transactionCurrentCurrency)
        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.amount)
            currency.text = money.currency.sign
        }
    }
}