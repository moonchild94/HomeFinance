package ru.divizdev.homefinance.presentation.operationslist.presenter

import android.util.Log
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.Operation

class DeleteOperationPresenter(private val repositoryOperation: RepositoryOperation) : AbstractDeleteOperationPresenter() {
    override fun onDeleteOperation(operation: Operation) {
        Completable.fromAction { repositoryOperation.delete(operation) }
                .subscribeOn(Schedulers.io())
                .doOnError { th -> Log.e(this.javaClass.simpleName, th.message) }
                .subscribe {}
    }
}