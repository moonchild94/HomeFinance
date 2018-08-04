package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.OperationType

interface RepositoryCategory {
    fun getAll(): List<Category>

    fun query(operationType: OperationType): List<Category>
}