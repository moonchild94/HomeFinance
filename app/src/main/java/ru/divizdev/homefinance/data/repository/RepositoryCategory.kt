package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.OperationType

interface RepositoryCategory {
    fun getAll(): Flowable<List<Category>>

    fun query(operationType: OperationType): Flowable<List<Category>>
}