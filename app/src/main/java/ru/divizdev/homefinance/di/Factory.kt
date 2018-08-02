package ru.divizdev.homefinance.di

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Build
import kotlinx.coroutines.experimental.launch
import ru.divizdev.homefinance.data.db.HomeFinanceDatabase
import ru.divizdev.homefinance.data.repository.*
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.model.Converter
import ru.divizdev.homefinance.model.UserWalletManager
import ru.divizdev.homefinance.presentation.LocaleUtils
import ru.divizdev.homefinance.presentation.Router
import ru.divizdev.homefinance.presentation.home.presenter.AbstractHomePresenter
import ru.divizdev.homefinance.presentation.home.presenter.HomePresenter
import ru.divizdev.homefinance.presentation.listTransaction.view.AbstractOperationListPresenter
import ru.divizdev.homefinance.presentation.listTransaction.view.OperationListPresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.OperationPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractWalletsPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.WalletsPresenter
import java.math.BigDecimal
import java.util.*

object Factory {
    private val router: Router = Router()
    private var localeUtils: LocaleUtils = LocaleUtils(null) //Для смены локали приходится перезаходить, нужно исправить
    private val mainPresenter = MainPresenter()
    private val repositoryCurrencyRate = RepositoryCurrencyRate()
    private val converter = Converter(repositoryCurrencyRate)
    private lateinit var repositoryWallet: RepositoryWallet
    private lateinit var repositoryOperation: RepositoryOperation
    private lateinit var userWalletManager: UserWalletManager

    fun create(context: Context) {
        val db = Room.databaseBuilder(context, HomeFinanceDatabase::class.java, "populus-database").build()
        repositoryWallet = RepositoryWalletImpl(db.getWalletDao())
        repositoryOperation = RepositoryOperationImpl(db.getOperationDao())
        launch {
            if (repositoryWallet.getAllWallets().isEmpty()) {
                repositoryWallet.addWallet(Wallet(name = "Кошелек", balance = Money(BigDecimal.valueOf(100), Currency.RUB)))

                db.getCategoryDao().insert(Category(operationType = OperationType.Expense, categoryName = "Еда", iconUri = ""))

                val wallet = repositoryWallet.getAllWallets()[0]
                val category = db.getCategoryDao().getAll()[0]
                val operation = IdleOperation(walletId = wallet.walletId ?: 0, comment = "бла",
                        sumCurrencyMain = Money(BigDecimal.valueOf(13.34), Currency.RUB), date = Date(),
                        categoryId = category.categoryId ?: 0)
                db.getOperationDao().insert(operation)
            }
        }

        userWalletManager = UserWalletManager(ru.divizdev.homefinance.di.Factory.repositoryWallet, converter)

        localeUtils = initUtils(context)
    }

    private fun initUtils(context: Context): LocaleUtils {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }

        return LocaleUtils(locale)
    }

    fun getLocaleUtils(): LocaleUtils {
        return localeUtils
    }

    fun getHomePresenter(): AbstractHomePresenter {
        return HomePresenter(userWalletManager)
    }

    fun getMainPresenter(): AbstractMainPresenter {
        return mainPresenter
    }

    fun getRouter(): Router {
        return router
    }

    fun getOperationListPresenter(): AbstractOperationListPresenter {
        return OperationListPresenter(repositoryOperation)
    }

    fun getWalletsPresenter(): AbstractWalletsPresenter {
        return WalletsPresenter(repositoryWallet)
    }

    fun getOperationPresenter(): AbstractOperationPresenter {
        return OperationPresenter(userWalletManager)
    }

    fun getConvertor(): Converter {
        return converter
    }
}