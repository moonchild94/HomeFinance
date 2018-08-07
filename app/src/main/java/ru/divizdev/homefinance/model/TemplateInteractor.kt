package ru.divizdev.homefinance.model

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.repository.RepositoryOperation
import ru.divizdev.homefinance.entities.Operation

class TemplateInteractor(private val repositoryOperation: RepositoryOperation) {
    fun addTemplate(operation: Operation) {
        repositoryOperation.add(operation)
    }

    fun getTemplates() : Flowable<List<Operation>> {
        return repositoryOperation.getTemplates()
    }
}