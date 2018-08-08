package ru.divizdev.homefinance.presentation.template.presenter

import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.model.TemplateInteractor
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter

class TemplateListPresenter(private val templateInteractor: TemplateInteractor,
                            private val repositoryOperation: RepositoryOperation,
                            parentPresenter: AbstractOperationPresenter) : AbstractTemplateListPresenter(parentPresenter) {

    private lateinit var templates: List<Operation>

    override fun loadTemplates() {
        templateInteractor.getTemplates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    templates = it
                    weakReferenceView.get()?.showTemplates(it)
                }
    }

    override fun onDeleteOperation(position: Int) {
        Completable.fromAction { repositoryOperation.delete(templates[position]) }
                .subscribeOn(Schedulers.io())
                .doOnError { th -> Log.e(this.javaClass.simpleName, th.message) }
                .subscribe {}
    }


    override fun selectTemplate(position: Int) {
        parentPresenter.selectTemplate(templates[position])
    }
}