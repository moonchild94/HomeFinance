package ru.divizdev.homefinance.data.db

import android.support.test.runner.AndroidJUnit4
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.CategoryType
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest : AbstractDaoTest() {

    private lateinit var categoryDao: CategoryDao
    private lateinit var outcomeCategory : Category
    private lateinit var incomeCategory : Category

    @Before
    override fun initDb() {
        super.initDb()
        categoryDao = homeFinanceDatabase.getCategoryDao()

        outcomeCategory = Category(1, CategoryType.OUTCOME, "food", "uri1")
        incomeCategory = Category(2, CategoryType.INCOME, "salary", "uri2")
        categoryDao.insertAll(listOf(outcomeCategory, incomeCategory))
    }

    @Test
    fun categoriesWithDifferentTypes_getByType_returnsListWithExpectedCategory() {
        categoryDao.getByType(CategoryType.OUTCOME)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 1 && it[0] == outcomeCategory
                }
    }

    @Test
    fun categoriesWithDifferentTypes_getAll_returnsListWithAllCategories() {
        categoryDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 2 && it.contains(outcomeCategory) && it.contains(incomeCategory)
                }
    }
}