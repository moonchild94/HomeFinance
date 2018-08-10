package ru.divizdev.homefinance.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.entities.CategoryType

@RunWith(JUnit4::class)
class RepositoryCategoryTest {

    private lateinit var categoryDao : CategoryDao
    private lateinit var repositoryCategory: RepositoryCategory

    @Before
    fun setUp() {
        categoryDao = Mockito.mock(CategoryDao::class.java)
        repositoryCategory = RepositoryCategoryImpl(categoryDao)
    }

    @Test
    fun getAll_verifyCallDaoGetAll() {
        repositoryCategory.getAll()

        verify(categoryDao).getAll()
        verifyNoMoreInteractions(categoryDao)
    }

    @Test
    fun getByType_verifyCallDaoGetByType() {
        val type = CategoryType.OUTCOME

        repositoryCategory.getByType(type)

        verify(categoryDao).getByType(type)
        verifyNoMoreInteractions(categoryDao)
    }
}