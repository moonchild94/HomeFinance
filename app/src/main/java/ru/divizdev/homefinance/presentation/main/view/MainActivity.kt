package ru.divizdev.homefinance.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.about.AboutDialog
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.settings.SettingsDialog
import ru.divizdev.homefinance.presentation.transaction.view.TransactionActivity

class MainActivity : BaseMvpActivity<AbstractMainPresenter, IMainView>(), IMainView {


    override fun getInstancePresenter(): AbstractMainPresenter {
        return MainPresenter()
    }

    override fun getMvpView(): IMainView {
        return this
    }

    private val TAG_ABOUT_DIALOG_FRAGMENT = "tagAbout"
    private val TAG_SETTINGS_DIALOG_FRAGMENT = "tagSettings"


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
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
        fab.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            this.startActivity(intent)
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

    override fun showAboutDialog() {
        val dialogFragment = AboutDialog()
        dialogFragment.show(
                supportFragmentManager,
                TAG_ABOUT_DIALOG_FRAGMENT
        )
    }
}
