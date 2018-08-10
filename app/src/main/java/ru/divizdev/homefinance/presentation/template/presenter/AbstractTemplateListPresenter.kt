package ru.divizdev.homefinance.presentation.template.presenter

import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import ru.divizdev.homefinance.presentation.operation.view.IOperationView
import ru.divizdev.homefinance.presentation.template.view.ITemplateListView

abstract class AbstractTemplateListPresenter(parentPresenter: AbstractOperationPresenter)
    : BaseMvpChildPresenter<ITemplateListView, IOperationView, AbstractOperationPresenter>(parentPresenter) {
    abstract fun loadTemplates()

    abstract fun onDeleteOperation(position: Int)

    abstract fun selectTemplate(position: Int)
}