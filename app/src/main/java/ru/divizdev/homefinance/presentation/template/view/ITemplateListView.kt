package ru.divizdev.homefinance.presentation.template.view

import ru.divizdev.homefinance.entities.Operation
import ru.divizdev.homefinance.mvp.IMvpView

interface ITemplateListView : IMvpView {
    fun showTemplates(templates: List<Operation>)
}