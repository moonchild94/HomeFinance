package ru.divizdev.homefinance.presentation.statistics.view

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.statistics.presenter.AbstractStatisticsMainPresenter

class StatisticsActivity : BaseMvpActivity<AbstractStatisticsMainPresenter, IStatisticsMainView>(), IStatisticsMainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistics)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, StatisticsFragment())
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getInstancePresenter(): AbstractStatisticsMainPresenter {
        return Factory.getStatisticsMainPresenter()
    }

    override fun getMvpView(): IStatisticsMainView {
       return this
    }
}