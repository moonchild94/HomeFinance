package ru.divizdev.homefinance.presentation.main.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.about.AboutDialog
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.TypeSubScreen
import ru.divizdev.homefinance.presentation.settings.SettingsDialog

class MainActivity : BaseMvpActivity<AbstractMainPresenter, IMainView>(), IMainView {

    override fun getOpenTypeScreen(): TypeSubScreen {
        when (navigation.selectedItemId) {
            R.id.navigation_home -> return TypeSubScreen.HOME
            R.id.navigation_operation -> return TypeSubScreen.OPERATIONS
            R.id.navigation_account -> return TypeSubScreen.WALLETS
        }
        return TypeSubScreen.UNDEFINE
    }

    override fun openAddOperation() {
        Factory.getRouter().navToOperation(this)
    }

    override fun openHome() {
        Factory.getRouter().navToHome(this)
    }

    override fun openWallets() {
        Factory.getRouter().navToWallets(this)
    }

    override fun openOperations() {
        Factory.getRouter().navToOperations(this)
    }

    override fun getInstancePresenter(): AbstractMainPresenter {
        return Factory.getMainPresenter()
    }

    override fun getMvpView(): IMainView {
        return this
    }

    private val TAG_ABOUT_DIALOG_FRAGMENT = "tagAbout"
    private val TAG_SETTINGS_DIALOG_FRAGMENT = "tagSettings"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener presenter.actionNavigationHome()
            }
            R.id.navigation_account -> {

                return@OnNavigationItemSelectedListener presenter.actionNavigationAccount()
            }
            R.id.navigation_operation -> {

                return@OnNavigationItemSelectedListener presenter.actionNavigationListOperation()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigation.selectedItemId = R.id.navigation_home
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.option_about -> {
                presenter.actionShowAbout()
                return true
            }
            R.id.option_settings -> {
                presenter.actionShowSettings()
                return true
            }
            R.id.option_statistics -> {
                presenter.actionShowStatistics()
                return true
            }
            else -> IllegalAccessException()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showErrorNotAvailable() {
        Toast.makeText(this, R.string.text_error_available, Toast.LENGTH_LONG).show()
    }

    override fun showSettingsDialog() {
        val dialogSettings = SettingsDialog()
        dialogSettings.show(
                supportFragmentManager,
                TAG_SETTINGS_DIALOG_FRAGMENT
        )
    }

    override fun showStatisticsDialog() {
        Factory.getRouter().navToStatistics(this)
    }

    override fun showAboutDialog() {
        val dialogFragment = AboutDialog()
        dialogFragment.show(
                supportFragmentManager,
                TAG_ABOUT_DIALOG_FRAGMENT
        )
    }
}
