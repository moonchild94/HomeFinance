package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.BaseMvpChildPresenter
import ru.divizdev.homefinance.presentation.operation.view.IAddOperationView
import ru.divizdev.homefinance.presentation.operation.view.IOperationView

abstract class AbstractAddOperationPresenter(parentPresenter: AbstractOperationPresenter)
    : BaseMvpChildPresenter<IAddOperationView, IOperationView, AbstractOperationPresenter>(parentPresenter) {

    abstract fun save()

    abstract fun saveTemplate()

    abstract fun openTemplates()

    abstract fun loadWallets()

    abstract fun loadCategories(categoryType: CategoryType)

    abstract fun initializeByTemplate(template: Operation?)
}