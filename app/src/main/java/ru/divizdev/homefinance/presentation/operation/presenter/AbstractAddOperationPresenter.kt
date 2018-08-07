package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.operation.view.IAddOperationView

abstract class AbstractAddOperationPresenter: BaseMvpPresenter<IAddOperationView>() {

    abstract fun save()

    abstract fun saveTemplate()

    abstract fun openTemplates()

    abstract fun loadWallets()

    abstract fun loadCategories(categoryType: CategoryType)
}