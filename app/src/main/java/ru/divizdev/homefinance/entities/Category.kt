package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.divizdev.homefinance.entities.OperationType

/**
 * Категория.
 */
@Entity
data class Category(
        @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
        val operationType: OperationType,
        val categoryName: String,
        val iconUri: String)
