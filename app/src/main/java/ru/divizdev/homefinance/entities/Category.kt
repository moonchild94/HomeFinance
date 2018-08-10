package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Категория.
 */
@Entity
@Parcelize
data class Category (
        @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
        val categoryType: CategoryType,
        val categoryName: String,
        val iconUri: String) : Parcelable
