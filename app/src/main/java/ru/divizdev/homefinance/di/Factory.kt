package ru.divizdev.homefinance.di

import android.content.Context
import android.os.Build
import ru.divizdev.homefinance.data.db.HomeFinanceDatabase
import ru.divizdev.homefinance.data.repository.*
import ru.divizdev.homefinance.model.Converter
import ru.divizdev.homefinance.model.OperationInteractor
import ru.divizdev.homefinance.presentation.LocaleUtils
import ru.divizdev.homefinance.presentation.Router
import ru.divizdev.homefinance.presentation.home.presenter.AbstractHomePresenter
import ru.divizdev.homefinance.presentation.home.presenter.HomePresenter
import ru.divizdev.homefinance.presentation.operationslist.view.AbstractOperationListPresenter
import ru.divizdev.homefinance.presentation.operationslist.view.OperationListPresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.OperationPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractAddWalletPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractWalletsPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.AddWalletPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.WalletsPresenter
import java.util.*

object Factory {
    private val router: Router = Router()
    private var localeUtils: LocaleUtils = LocaleUtils(null) //Для смены локали приходится перезаходить, нужно исправить
    private val mainPresenter = MainPresenter()
    private val repositoryCurrencyRate = RepositoryCurrencyRate()
    private val converter = Converter(repositoryCurrencyRate)

    private lateinit var repositoryWallet: RepositoryWallet
    private lateinit var repositoryOperation: RepositoryOperation
    private lateinit var repositoryCategory: RepositoryCategory
    private lateinit var repositoryWalletOperation: RepositoryWalletOperation

    private lateinit var operationInteractor: OperationInteractor

    fun create(context: Context) {
        val db = HomeFinanceDatabase.getDataBase(context)
        repositoryWallet = RepositoryWalletImpl(db.getWalletDao())
        repositoryOperation = RepositoryOperationImpl(db.getOperationDao())
        repositoryWalletOperation = RepositoryWalletOperationImpl(db.getWalletOperationDao())
        repositoryCategory = RepositoryCategoryImpl(db.getCategoryDao())

        operationInteractor = OperationInteractor(repositoryWalletOperation, repositoryWallet, repositoryOperation, converter)

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
        return HomePresenter(operationInteractor)
    }

    fun getMainPresenter(): AbstractMainPresenter {
        return mainPresenter
    }

    fun getRouter(): Router {
        return router
    }

    fun getOperationListPresenter(): AbstractOperationListPresenter {
        return OperationListPresenter(repositoryOperation, repositoryWallet, repositoryWalletOperation)
    }

    fun getWalletsPresenter(): AbstractWalletsPresenter {
        return WalletsPresenter(repositoryWallet)
    }

    fun getOperationPresenter(): AbstractOperationPresenter {
        return OperationPresenter(repositoryWallet, repositoryCategory, operationInteractor)
    }

    fun getAddWalletPresenter(): AbstractAddWalletPresenter {
        return AddWalletPresenter(repositoryWallet)
    }

    fun getConvertor(): Converter {
        return converter
    }
}