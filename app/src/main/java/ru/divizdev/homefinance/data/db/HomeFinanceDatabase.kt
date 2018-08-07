package ru.divizdev.homefinance.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.entities.Currency
import java.math.BigDecimal

@Database(entities = [Category::class, Wallet::class, IdleOperation::class], version = 1)
@TypeConverters(Converters::class)
abstract class HomeFinanceDatabase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getWalletDao(): WalletDao

    abstract fun getOperationDao(): OperationDao

    abstract fun getWalletOperationDao(): WalletOperationDao

    companion object {
        @Volatile
        private var INSTANCE: HomeFinanceDatabase? = null

        fun getInstance(context: Context): HomeFinanceDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: getDataBase(context).also { INSTANCE = it }
                }

        fun getDataBase(context: Context): HomeFinanceDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, HomeFinanceDatabase::class.java, "homefinance-db")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                Completable.fromAction {
                                    val wallet1 = Wallet(1, "Наличные", Money(BigDecimal.valueOf(100), Currency.RUB))
                                    getInstance(context).getWalletDao().insert(wallet1)
                                    val wallet2 = Wallet(2, "Карта", Money(BigDecimal.valueOf(10000), Currency.USD))
                                    getInstance(context).getWalletDao().insert(wallet2)

                                    val category1 = Category(1, CategoryType.OUTCOME, "Еда", "")
                                    getInstance(context).getCategoryDao().insert(category1)
                                    val category2 = Category(2, CategoryType.OUTCOME, "Развлечения", "")
                                    getInstance(context).getCategoryDao().insert(category2)
                                    val category3 = Category(3, CategoryType.OUTCOME, "Транспорт", "")
                                    getInstance(context).getCategoryDao().insert(category3)

                                    val category4 = Category(4, CategoryType.INCOME, "Зарплата", "")
                                    getInstance(context).getCategoryDao().insert(category4)
                                    val category5 = Category(5, CategoryType.INCOME, "Рента", "")
                                    getInstance(context).getCategoryDao().insert(category5)
                                    val category6 = Category(6, CategoryType.INCOME, "Подработка", "")
                                    getInstance(context).getCategoryDao().insert(category6)
                                }.subscribeOn(Schedulers.io()).subscribe {}
                            }
                        })
                        .build()
            }
            return INSTANCE as HomeFinanceDatabase
        }
    }
}