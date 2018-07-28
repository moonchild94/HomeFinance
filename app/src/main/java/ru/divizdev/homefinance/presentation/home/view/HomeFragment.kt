package ru.divizdev.homefinance.presentation.home.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.LocaleUtils
import ru.divizdev.homefinance.presentation.home.presenter.AbstractHomePresenter
import ru.divizdev.homefinance.presentation.home.presenter.HomePresenter
import java.util.*


class HomeFragment : BaseMvpFragment<AbstractHomePresenter, IHomeView>(), IHomeView {
    override fun getMvpView(): IHomeView {
        return this
    }

    //Заготовка для получения Presenter, пока прямая инициализация
    override fun getInstancePresenter(): AbstractHomePresenter {
        return HomePresenter()
    }

    private var localeUtils: LocaleUtils? = null


    private fun setMoney(money: Money, value: TextView, currency: TextView) {
        value.text = localeUtils?.formatBigDecimal(money.value) ?: money.value.toString()
        currency.text = localeUtils?.formatCurrency(money.currency) ?: money.currency.name
    }

    override fun setMainBalance(balance: Money) {
        setMoney(balance, balance_main_currency_text_view, main_currency_text_view)
    }

    override fun setSecondaryBalance(balance: Money) {
        setMoney(balance, balance_second_currency_text_view, second_currency_text_view)
    }

    override fun setRevenue(balance: Money) {
        setMoney(balance, value_revenue_text_view, revenue_currency_text_view)
    }

    override fun setExpense(balance: Money) {
        setMoney(balance, value_expense_text_view, expense_currency_text_view)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }

        localeUtils = LocaleUtils(locale)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}
