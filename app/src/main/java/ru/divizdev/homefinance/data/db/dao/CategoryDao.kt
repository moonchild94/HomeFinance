package ru.divizdev.homefinance.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.divizdev.homefinance.entities.Category

/**
 * Dao для работы с категориями.
 */
@Dao
interface CategoryDao {

    // Добавление Category в бд
    @Insert
    fun insert(category: Category)

    // Удаление Category из бд
    @Delete
    fun delete(category: Category)

    // Получение всех Category из бд
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>
}