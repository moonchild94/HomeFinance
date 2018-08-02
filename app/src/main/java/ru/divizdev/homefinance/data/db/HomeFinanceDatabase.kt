package ru.divizdev.homefinance.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.divizdev.homefinance.entities.Category
import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Wallet
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletDao

@Database(entities = [Category::class, Wallet::class, IdleOperation::class], version = 1)
@TypeConverters(Converters::class)
abstract class HomeFinanceDatabase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getWalletDao(): WalletDao

    abstract fun getOperationDao(): OperationDao
}