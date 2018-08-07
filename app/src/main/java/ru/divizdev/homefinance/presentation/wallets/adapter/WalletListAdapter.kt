package ru.divizdev.homefinance.presentation.wallets.adapter

import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.item_wallet.view.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.presentation.LocaleUtils

class WalletListAdapter(private var listWallets: List<Wallet>,
                        private val localeUtils: LocaleUtils,
                        private val onDeleteAction: (position: Int) -> Unit,
                        private val onEditAction: (position: Int) -> Unit) : RecyclerView.Adapter<WalletListAdapter.ViewHolder>() {

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

    inner class ViewHolder(view: View, private val localeUtils: LocaleUtils) : RecyclerView.ViewHolder(view),
            View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {

        private lateinit var wallet: Wallet

        init {
            view.setOnCreateContextMenuListener(this)
        }

        fun setData(wallet: Wallet) {
            this.wallet = wallet
            itemView.wallet_name_text_view.text = wallet.walletName
            itemView.wallet_main_currency_text_view.text = wallet.balance.currency.name

            setMoney(wallet.balance, itemView.wallet_balance_text_view, itemView.wallet_currency_balance_text_view)
        }

        override fun onCreateContextMenu(menu: ContextMenu, selectedView: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu.add(0, selectedView.id, 0, "Редактировать").setOnMenuItemClickListener(this)
            menu.add(0, selectedView.id, 1, "Удалить").setOnMenuItemClickListener(this)
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                when (menuItem.order) {
                    0 -> onEditAction.invoke(adapterPosition)
                    1 -> onDeleteAction.invoke(adapterPosition)
                    else -> throw IllegalArgumentException()
                }
            }
            return true
        }

        private fun setMoney(money: Money, value: TextView, currency: TextView) {
            value.text = localeUtils.formatBigDecimal(money.amount)
            currency.text = money.currency.sign
        }
    }
}