package ru.divizdev.homefinance.presentation

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.operation.view.OperationActivity
import ru.divizdev.homefinance.presentation.operationslist.view.OperationListFragment
import ru.divizdev.homefinance.presentation.statistics.view.StatisticsFragment
import ru.divizdev.homefinance.presentation.wallets.ui.WalletsFragment

class Router {

    fun navToHome(activity: AppCompatActivity) {
        replaceMainFragment(activity, HomeFragment())
    }

    fun navToWallets(activity: AppCompatActivity) {
        replaceMainFragment(activity, WalletsFragment())
    }

    fun navToOperations(activity: AppCompatActivity) {
        replaceMainFragment(activity, OperationListFragment())
    }

    fun navToStatistics(activity: AppCompatActivity) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, StatisticsFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    fun navToAddOperation(activity: AppCompatActivity) {
        val intent = Intent(activity, OperationActivity::class.java)
        activity.startActivity(intent)
    }

    private fun replaceMainFragment(activity: AppCompatActivity, fragment: Fragment) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
    }
}