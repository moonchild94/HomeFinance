package ru.divizdev.homefinance.presentation.main.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.presentation.about.AboutDialog
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.main.presenter.IMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.settings.SettingsDialog

class MainActivity : AppCompatActivity(), IMainView {

    private val TAG_ABOUT_DIALOG_FRAGMENT = "tagAbout"
    private val TAG_SETTINGS_DIALOG_FRAGMENT = "tagSettings"

    private val presenter: IMainPresenter = MainPresenter()

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
        val fragmentManager = supportFragmentManager
        var fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = HomeFragment()
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
        presenter.attachView(this)
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

    override fun showErrorNotAvailable(){
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

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }
}
