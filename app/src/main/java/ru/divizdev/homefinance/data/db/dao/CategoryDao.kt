package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.CategoryType

/**
 * Dao для работы с категориями.
 */
@Dao
interface CategoryDao {

    @Insert
    fun insertAll(category: List<Category>)

    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM category")
    fun getAll(): Flowable<List<Category>>

    @Query("SELECT * FROM category WHERE Category.categoryType = :categoryType")
    fun query(categoryType: CategoryType): Flowable<List<Category>>
}