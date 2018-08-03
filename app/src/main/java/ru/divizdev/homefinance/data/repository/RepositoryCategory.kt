package ru.divizdev.homefinance.data.repository

import ru.divizdev.homefinance.entities.Category

interface RepositoryCategory {
    fun getAll(): List<Category>
}