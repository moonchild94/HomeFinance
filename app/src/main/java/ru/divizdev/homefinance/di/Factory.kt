package ru.divizdev.homefinance.di

import android.content.Context
import android.os.Build
import ru.divizdev.homefinance.data.FakeRepositoryWallet
import ru.divizdev.homefinance.data.IRepositoryWallet
import ru.divizdev.homefinance.data.RepositoryCurrencyRate
import ru.divizdev.homefinance.model.Converter
import ru.divizdev.homefinance.model.UserWalletManager
import ru.divizdev.homefinance.presentation.LocaleUtils
import ru.divizdev.homefinance.presentation.Router
import ru.divizdev.homefinance.presentation.home.presenter.AbstractHomePresenter
import ru.divizdev.homefinance.presentation.home.presenter.HomePresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.transaction.presenter.AbstractTransactionPresenter
import ru.divizdev.homefinance.presentation.transaction.presenter.TransactionPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.AbstractWalletsPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.WalletsPresenter
import java.util.*

object Factory {
    private val router: Router = Router()
    private var localeUtils: LocaleUtils = LocaleUtils(null) //Для смены локали приходится перезаходить, нужно исправить
    private val mainPresenter = MainPresenter()
    private val repositoryCurrencyRate = RepositoryCurrencyRate()
    private val converter = Converter(repositoryCurrencyRate)
    private val repositoryWallet: IRepositoryWallet = FakeRepositoryWallet()
    private val userWalletManager: UserWalletManager = UserWalletManager(repositoryWallet, converter)


    fun create(context: Context) {
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

    fun getLocaleUtils() :LocaleUtils{
        return localeUtils
    }

    fun getHomePresenter(): AbstractHomePresenter {
        return HomePresenter(userWalletManager)
    }

    fun getMainPresenter(): AbstractMainPresenter{
        return mainPresenter
    }

    fun getRouter(): Router {
        return router
    }

    fun getWalletsPresenter(): AbstractWalletsPresenter{
        return  WalletsPresenter(repositoryWallet)
    }

    fun getTransactionPresenter():AbstractTransactionPresenter{
        return TransactionPresenter(userWalletManager)
    }

    fun getConvertor(): Converter{
        return converter
    }
}