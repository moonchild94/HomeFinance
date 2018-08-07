package ru.divizdev.homefinance.presentation.template.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.model.TemplateInteractor

class TemplateListPresenter(private val templateInteractor: TemplateInteractor) : AbstractTemplateListPresenter() {
    private lateinit var templates: List<Operation>

    override fun loadTemplates() {
        templateInteractor.getTemplates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    templates = it
                    weakReferenceView.get()?.showTemplates(it)
                }
    }
}