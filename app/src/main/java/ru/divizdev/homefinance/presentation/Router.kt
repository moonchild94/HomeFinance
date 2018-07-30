package ru.divizdev.homefinance.presentation

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.presentation.home.view.HomeFragment
import ru.divizdev.homefinance.presentation.listTransaction.ui.ListTransactionFragment
import ru.divizdev.homefinance.presentation.transaction.view.TransactionActivity
import ru.divizdev.homefinance.presentation.wallets.ui.WalletsFragment

class Router {

    fun navToHome(activity: AppCompatActivity){
        replaceMainFragment(activity, HomeFragment())
    }

    fun navToWallets(activity: AppCompatActivity){
        replaceMainFragment(activity, WalletsFragment())
    }

    fun navToTransactions(activity: AppCompatActivity){
        replaceMainFragment(activity, ListTransactionFragment())
    }

    fun navToAddTransaction(activity: AppCompatActivity){
        val intent = Intent(activity, TransactionActivity::class.java)
        activity.startActivity(intent)
    }

    private fun replaceMainFragment(activity: AppCompatActivity, fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}