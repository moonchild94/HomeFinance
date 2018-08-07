package ru.divizdev.homefinance.presentation.template.presenter

import ru.divizdev.homefinance.mvp.BaseMvpPresenter
import ru.divizdev.homefinance.presentation.template.view.ITemplateListView

abstract class AbstractTemplateListPresenter : BaseMvpPresenter<ITemplateListView>() {
    abstract fun loadTemplates()
}