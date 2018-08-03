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
import ru.divizdev.homefinance.presentation.LocaleUtils

class ListWalletsAdapter(private var listWallets: List<Wallet>, private val localeUtils: LocaleUtils) : RecyclerView.Adapter<ListWalletsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet,
                parent, false)
        return ViewHolder(view, localeUtils)
    }

    fun updateData(newListWallets: List<Wallet>) {
        listWallets = newListWallets
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listWallets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listWallets[position])
    }

    class ViewHolder(view: View, private val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view) {

        private lateinit var wallet: Wallet

        fun setData(wallet: Wallet) {
            this.wallet = wallet
            itemView.wallet_name_text_view.text = wallet.walletName
            itemView.wallet_main_currency_text_view.text = wallet.balance.currency.name

            setMoney(wallet.balance, itemView.wallet_balance_text_view, itemView.wallet_currency_balance_text_view)
        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.amount)
            currency.text = money.currency.sign
        }
    }
}