package ru.divizdev.homefinance.data.repository

import io.reactivex.Flowable
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.CategoryType

class RepositoryCategoryImpl(private val categoryDao: CategoryDao) : RepositoryCategory {
    override fun getAll(): Flowable<List<Category>> {
        return categoryDao.getAll()
    }

    override fun getByType(categoryType: CategoryType): Flowable<List<Category>> {
        return categoryDao.getByType(categoryType)
    }
}