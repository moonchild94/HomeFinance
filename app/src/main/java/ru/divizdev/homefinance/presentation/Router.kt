package ru.divizdev.homefinance.presentation

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.operation.view.AddOperationFragment
import ru.divizdev.homefinance.presentation.operation.view.OperationActivity
import ru.divizdev.homefinance.presentation.operationslist.view.OperationListFragment
import ru.divizdev.homefinance.presentation.statistics.view.StatisticsActivity
import ru.divizdev.homefinance.presentation.statistics.view.StatisticsFragment
import ru.divizdev.homefinance.presentation.template.view.TemplateListFragment
import ru.divizdev.homefinance.presentation.wallets.ui.WalletsFragment

class Router {

    fun navToHome(activity: FragmentActivity) {
        replaceFragment(activity, HomeFragment())
    }

    fun navToWallets(activity: FragmentActivity) {
        replaceFragment(activity, WalletsFragment())
    }

    fun navToOperations(activity: AppCompatActivity) {
        replaceFragment(activity, OperationListFragment())
    }

    fun navToStatistics(activity: FragmentActivity) {
        val intent = Intent(activity, StatisticsActivity::class.java)
        activity.startActivity(intent)
    }

    fun navToOperation(activity: FragmentActivity) {
        val intent = Intent(activity, OperationActivity::class.java)
        activity.startActivity(intent)
    }

    fun navToAddOperation(activity: FragmentActivity, template: Operation?) {
        activity.supportFragmentManager.popBackStack()
        replaceFragment(activity, AddOperationFragment.newInstance(template))
    }

    fun navToTemplates(activity: FragmentActivity) {
        activity.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, TemplateListFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    private fun replaceFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
    }
}