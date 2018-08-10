package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.CategoryType

interface RepositoryCategory {
    fun getAll(): Flowable<List<Category>>

    fun getByType(categoryType: CategoryType): Flowable<List<Category>>
}