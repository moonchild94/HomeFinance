package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.OperationType

class RepositoryCategoryImpl(private val categoryDao: CategoryDao) : RepositoryCategory {
    override fun getAll(): Flowable<List<Category>> {
        return categoryDao.getAll()
    }

    override fun query(operationType: OperationType): Flowable<List<Category>> {
        return categoryDao.query(operationType)
    }
}