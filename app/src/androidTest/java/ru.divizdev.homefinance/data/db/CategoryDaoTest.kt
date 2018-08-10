package ru.divizdev.homefinance.data.db

import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.divizdev.homefinance.data.db.dao.CategoryDao

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest : AbstractDaoTest() {

    private lateinit var categoryDao: CategoryDao

    @Before
    override fun initDb() {
        super.initDb()
        categoryDao = homeFinanceDatabase.getCategoryDao()
    }

    @Test
    fun newCategory_getAll_returnsListWithTheCategory() {

    }
}