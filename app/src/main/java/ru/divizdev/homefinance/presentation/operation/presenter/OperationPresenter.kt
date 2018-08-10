package ru.divizdev.homefinance.presentation.operation.presenter

import ru.divizdev.homefinance.entities.Operation

class OperationPresenter : AbstractOperationPresenter() {
    override fun selectTemplate(template: Operation) {
        weakReferenceView.get()?.openAddOperation(template)
    }
}