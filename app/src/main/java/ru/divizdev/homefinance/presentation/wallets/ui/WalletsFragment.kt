package ru.divizdev.homefinance.presentation.wallets.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_wallets.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.wallets.adapter.ListWalletsAdapter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractWalletsPresenter


class WalletsFragment : BaseMvpFragment<AbstractWalletsPresenter, IWalletsView>(), IWalletsView {

    private var list: MutableList<Wallet> = mutableListOf()

    override fun getInstancePresenter(): AbstractWalletsPresenter {
        return Factory.getWalletsPresenter()
    }

    override fun getMvpView(): IWalletsView {
        return this
    }

    override fun setListWallets(list: Collection<Wallet>) {
        this.list.clear()
        this.list.addAll(list)
        wallets_recycler_view.adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_wallets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(view.context)
        wallets_recycler_view.layoutManager = linearLayoutManager
        val listWalletsAdapter = ListWalletsAdapter(list, Factory.getLocaleUtils())
        wallets_recycler_view.adapter = listWalletsAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }



}
