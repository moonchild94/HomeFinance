package ru.divizdev.homefinance.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.db.dao.CategoryDao
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.entities.Currency
import java.math.BigDecimal
import java.util.*

@Database(entities = [Category::class, Wallet::class, IdleOperation::class], version = 1)
@TypeConverters(Converters::class)
abstract class HomeFinanceDatabase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getWalletDao(): WalletDao

    abstract fun getOperationDao(): OperationDao

    abstract fun getWalletOperationDao(): WalletOperationDao

    companion object {
        @Volatile private var INSTANCE: HomeFinanceDatabase? = null

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

                                launch {
                                    val wallet1 = Wallet(1, "Кошелек", Money(BigDecimal.valueOf(100), Currency.RUB))
                                    getInstance(context).getWalletDao().insert(wallet1)
                                    val wallet2 = Wallet(2, "Кошелек2", Money(BigDecimal.valueOf(10000), Currency.USD))
                                    getInstance(context).getWalletDao().insert(wallet2)

                                    val category = Category(1, OperationType.OUTCOME, "Еда", "")
                                    getInstance(context).getCategoryDao().insert(category)

                                    val operation1 = IdleOperation(walletId = wallet1.walletId, comment = "Обед",
                                            sumCurrencyMain = Money(BigDecimal.valueOf(13.34), Currency.RUB), date = Date(),
                                            categoryId = category.categoryId)
                                    getInstance(context).getWalletOperationDao().insertOperationAndUpdateWallet(operation1, wallet1)
                                    val operation2 = IdleOperation(walletId = wallet2.walletId, comment = "Ужин",
                                            sumCurrencyMain = Money(BigDecimal.valueOf(230.34), Currency.USD), date = Date(),
                                            categoryId = category.categoryId)
                                    getInstance(context).getWalletOperationDao().insertOperationAndUpdateWallet(operation2, wallet2)
                                }
                            }
                        })
                        .build()
            }
            return INSTANCE as HomeFinanceDatabase
        }
    }
}