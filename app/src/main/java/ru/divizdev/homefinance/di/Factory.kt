package ru.divizdev.homefinance.di

import android.content.Context
import android.os.Build
import ru.divizdev.homefinance.data.db.HomeFinanceDatabase
import ru.divizdev.homefinance.data.repository.*
import ru.divizdev.homefinance.model.*
import ru.divizdev.homefinance.presentation.LocaleUtils
import ru.divizdev.homefinance.presentation.Router
import ru.divizdev.homefinance.presentation.home.presenter.AbstractHomePresenter
import ru.divizdev.homefinance.presentation.home.presenter.HomePresenter
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.presenter.MainPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractAddOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AbstractOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.AddOperationPresenter
import ru.divizdev.homefinance.presentation.operation.presenter.OperationPresenter
import ru.divizdev.homefinance.presentation.operationslist.presenter.AbstractDeleteOperationPresenter
import ru.divizdev.homefinance.presentation.operationslist.presenter.AbstractOperationListPresenter
import ru.divizdev.homefinance.presentation.operationslist.presenter.DeleteOperationPresenter
import ru.divizdev.homefinance.presentation.operationslist.presenter.OperationListPresenter
import ru.divizdev.homefinance.presentation.statistics.presenter.AbstractStatisticsPresenter
import ru.divizdev.homefinance.presentation.statistics.presenter.StatisticsPresenter
import ru.divizdev.homefinance.presentation.template.presenter.AbstractTemplateListPresenter
import ru.divizdev.homefinance.presentation.template.presenter.TemplateListPresenter
import ru.divizdev.homefinance.presentation.wallets.presenter.*
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

    private lateinit var summaryInteractor: SummaryInteractor
    private lateinit var operationInteractor: OperationInteractor
    private lateinit var statisticsInteractor: StatisticsInteractor
    private lateinit var templateInteractor: TemplateInteractor

    fun create(context: Context) {
        val db = HomeFinanceDatabase.getDataBase(context)
        repositoryWallet = RepositoryWalletImpl(db.getWalletDao())
        repositoryOperation = RepositoryOperationImpl(db.getOperationDao(), db.getWalletOperationDao())
        repositoryCategory = RepositoryCategoryImpl(db.getCategoryDao())

        operationInteractor = OperationInteractor(repositoryOperation, converter)
        summaryInteractor = SummaryInteractor(repositoryWallet, repositoryOperation, converter)
        statisticsInteractor = StatisticsInteractor(repositoryOperation)
        templateInteractor = TemplateInteractor(repositoryOperation)

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

    fun getHomePresenter(parentPresenter: AbstractMainPresenter): AbstractHomePresenter {
        return HomePresenter(summaryInteractor, parentPresenter)
    }

    fun getMainPresenter(): AbstractMainPresenter {
        return mainPresenter
    }

    fun getRouter(): Router {
        return router
    }

    fun getOperationListPresenter(parentPresenter: AbstractMainPresenter): AbstractOperationListPresenter {
        return OperationListPresenter(operationInteractor, repositoryWallet, parentPresenter)
    }

    fun getWalletsPresenter(parentPresenter: AbstractMainPresenter): AbstractWalletsPresenter {
        return WalletsPresenter(repositoryWallet, parentPresenter)
    }

    fun getOperationPresenter(): AbstractOperationPresenter {
        return OperationPresenter()
    }

    fun getAddOperationPresenter(parentPresenter: AbstractOperationPresenter): AbstractAddOperationPresenter {
        return AddOperationPresenter(repositoryWallet, repositoryCategory, operationInteractor, templateInteractor, parentPresenter)
    }

    fun getDeleteOperationPresenter(): AbstractDeleteOperationPresenter {
        return DeleteOperationPresenter(repositoryOperation)
    }


    fun getAddWalletPresenter(): AbstractAddWalletPresenter {
        return AddWalletPresenter(repositoryWallet)
    }

    fun getDeleteWalletPresenter(): DeleteWalletPresenter {
        return DeleteWalletPresenter(repositoryWallet)
    }

    fun getEditWalletPresenter(): EditWalletPresenter {
        return EditWalletPresenter(repositoryWallet)
    }

    fun getStatisticsPresenter(parentPresenter: AbstractMainPresenter): AbstractStatisticsPresenter {
        return StatisticsPresenter(repositoryWallet, statisticsInteractor, parentPresenter)
    }

    fun getTemplateListPresenter(parentPresenter: AbstractOperationPresenter): AbstractTemplateListPresenter {
        return TemplateListPresenter(templateInteractor, repositoryOperation, parentPresenter)
    }

    fun getConvertor(): Converter {
        return converter
    }
}