package ru.divizdev.homefinance.presentation.operation.view

import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter

class OperationActivity : BaseMvpActivity<AbstractOperationPresenter, IOperationView>(), IOperationView {

    override fun getInstancePresenter(): AbstractOperationPresenter {
        return Factory.getOperationPresenter()
    }

    override fun getMvpView(): IOperationView {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            Factory.getRouter().navToAddOperation(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
