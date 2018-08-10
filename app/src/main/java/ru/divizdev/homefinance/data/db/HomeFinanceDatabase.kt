package ru.divizdev.homefinance.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.divizdev.homefinance.R
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
                                    val wallet1 = Wallet(1, context.getString(R.string.cash), Money(BigDecimal.valueOf(100), Currency.RUB))
                                    getInstance(context).getWalletDao().insert(wallet1)
                                    val wallet2 = Wallet(2, context.getString(R.string.card), Money(BigDecimal.valueOf(10000), Currency.USD))
                                    getInstance(context).getWalletDao().insert(wallet2)

                                    getInstance(context).getCategoryDao().insertAll(createIncomeCategories(context))
                                    getInstance(context).getCategoryDao().insertAll(createOutcomeCategories(context))

                                }.subscribeOn(Schedulers.io()).subscribe {}
                            }
                        })
                        .build()
            }
            return INSTANCE as HomeFinanceDatabase
        }

        private fun createIncomeCategories(context: Context) : List<Category> {
            val category1 = Category(1, CategoryType.OUTCOME, context.getString(R.string.food_category), "ic_food")
            val category2 = Category(2, CategoryType.OUTCOME, context.getString(R.string.entertainment_category), "ic_movie")
            val category3 = Category(3, CategoryType.OUTCOME, context.getString(R.string.transport_category), "ic_car")
            val category4 = Category(4, CategoryType.OUTCOME, context.getString(R.string.education_category), "ic_education")
            val category5 = Category(5, CategoryType.OUTCOME, context.getString(R.string.shopping_category), "ic_shopping")
            val category6 = Category(6, CategoryType.OUTCOME, context.getString(R.string.accommodation_category), "ic_home")
            val category7 = Category(7, CategoryType.OUTCOME, context.getString(R.string.health_category), "ic_health")
            val category8 = Category(8, CategoryType.OUTCOME, context.getString(R.string.vacation_category),  "ic_trip")
            val category9 = Category(9, CategoryType.OUTCOME, context.getString(R.string.other_outcome_category), "ic_shopping-other")
            return listOf(category1, category2, category3, category4, category5, category6, category7, category8, category9)
        }

        private fun createOutcomeCategories(context: Context) : List<Category> {
            val category1 = Category(10, CategoryType.INCOME, context.getString(R.string.salary_category), "ic_salary")
            val category2 = Category(11, CategoryType.INCOME, context.getString(R.string.gifts_category), "ic_gift")
            val category3 = Category(12, CategoryType.INCOME, context.getString(R.string.alimony_category), "ic_alimony")
            val category4 = Category(13, CategoryType.INCOME, context.getString(R.string.cash_back_category), "ic_cash-back")
            val category5 = Category(14, CategoryType.INCOME, context.getString(R.string.dividends_category),  "ic_dividends")
            val category6 = Category(15, CategoryType.INCOME, context.getString(R.string.salary_category),  "ic_sale")
            val category7 = Category(16, CategoryType.INCOME, context.getString(R.string.other_income_category), "ic_other")
            return listOf(category1, category2, category3, category4, category5, category6, category7)
        }
    }
}