package ru.divizdev.homefinance.presentation.wallets.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_wallet.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.entities.emptyWallet
import ru.divizdev.homefinance.presentation.LocaleUtils

class ListWalletsAdapter(val listWallets: List<Wallet>, val localeUtils: LocaleUtils) : RecyclerView.Adapter<ListWalletsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet,
                parent, false)
        return ViewHolder(view, localeUtils)
    }

    override fun getItemCount(): Int {
        return listWallets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listWallets[position])
    }

    class ViewHolder(view: View, val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view) {

        private var wallet: Wallet = emptyWallet()

        fun setData(wallet: Wallet) {
            this.wallet = wallet
            itemView.transaction_category_text_view.text = wallet.name
            itemView.transaction_main_currency_text_view.text = wallet.mainCurrency.name

            setMoney(wallet.getBalance(), itemView.transaction_balance_text_view, itemView.transaction_currency_balance_text_view)

        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.value)
            currency.text = localeUtils.formatCurrency(money.currency)
        }
    }
}