package ru.divizdev.homefinance.presentation.operationslist.adapter

import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_operation.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.entities.OperationType
import ru.divizdev.homefinance.presentation.LocaleUtils

class OperationListAdapter(private var listOperations: List<Operation>,
                           private val localeUtils: LocaleUtils,
                           private val onLongClickAction: (position: Int) -> Unit,
                           private val onClickAction: (position: Int) -> Unit) : RecyclerView.Adapter<OperationListAdapter.ViewHolder>() {
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

        private var transactionCategoryName = itemView.transaction_category_name_image_view
        private var transactionCategoryComment = itemView.transaction_category_comment_text_view
        private var transactionWalletName = itemView.transaction_wallet_text_view
        private var transactionMainAmount = itemView.transaction_value_transaction_currency_text_view
        private var transactionMainCurrency = itemView.transaction_main_currency_text_view
        private var transactionCurrentAmount = itemView.transaction_balance_text_view
        private var transactionCurrentCurrency = itemView.transaction_currency_balance_text_view
        private var transactionPeriodTitle = itemView.period_title
        private var transactionPeriodValue = itemView.period_value

        private var operation: Operation? = null

        init {
            view.setOnLongClickListener {
                onLongClickAction.invoke(adapterPosition)
                true
            }
            view.setOnClickListener { onClickAction.invoke(adapterPosition) }
        }

        fun setData(operation: Operation) {
            this.operation = operation

            val imageUri = Uri.parse("android.resource://ru.divizdev.homefinance/drawable/" + operation.category.iconUri)
            transactionCategoryName.setImageURI(imageUri)
            transactionCategoryComment.text = operation.comment
            transactionWalletName.text = operation.wallet.walletName

            if (operation.sumCurrencyOperation.currency != operation.sumCurrencyMain.currency) {
                setMoney(operation.sumCurrencyMain, transactionMainAmount, transactionMainCurrency)
            } else {
                transactionMainAmount.text = ""
                transactionMainCurrency.text = ""
            }
            setMoney(operation.sumCurrencyOperation, transactionCurrentAmount, transactionCurrentCurrency)

            val colorId = if (operation.category.categoryType == CategoryType.OUTCOME) R.color.expenseText else R.color.revenueText
            val moneyColor = ContextCompat.getColor(itemView.context, colorId)
            transactionMainAmount.setTextColor(moneyColor)
            transactionMainCurrency.setTextColor(moneyColor)
            transactionCurrentAmount.setTextColor(moneyColor)
            transactionCurrentCurrency.setTextColor(moneyColor)

            if (operation.operationType == OperationType.PERIODIC) {
                transactionPeriodTitle.setText(R.string.period_in_days_title)
                transactionPeriodValue.text = operation.period.toString()
            } else if (operation.operationType == OperationType.COMPLETE) {
                transactionPeriodTitle.text = ""
                transactionPeriodValue.text = ""
            }
        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.amount)
            currency.text = money.currency.sign
        }
    }
}