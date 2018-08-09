package ru.divizdev.homefinance.presentation.operationslist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpDialogFragment
import ru.divizdev.homefinance.presentation.operationslist.presenter.AbstractDeleteOperationPresenter

const val DELETING_OPERATION_KEY = "deletingOperationKey"

class DeleteOperationDialogFragment : BaseMvpDialogFragment<AbstractDeleteOperationPresenter, IDeleteOperationView>(), IDeleteOperationView {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val operation = arguments?.getParcelable<Operation>(DELETING_OPERATION_KEY)
        return AlertDialog.Builder(context)
                .setTitle(getString(R.string.delete_operation_confirmation))
                .setPositiveButton(getString(R.string.ok)) { _, _ -> if (operation != null) presenter.onDeleteOperation(operation) }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
    }

    companion object {
        @JvmStatic
        fun newInstance(operation: Operation): DeleteOperationDialogFragment {
            return DeleteOperationDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DELETING_OPERATION_KEY, operation)
                }
            }
        }
    }

    override fun getMvpView(): IDeleteOperationView {
        return this
    }

    override fun getInstancePresenter(): AbstractDeleteOperationPresenter {
        return Factory.getDeleteOperationPresenter()
    }

}