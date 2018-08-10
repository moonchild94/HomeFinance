package ru.divizdev.homefinance.data.repository

import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ru.divizdev.homefinance.data.db.dao.OperationDao
import ru.divizdev.homefinance.data.db.dao.WalletOperationDao
import ru.divizdev.homefinance.entities.*
import ru.divizdev.homefinance.entities.Currency
import java.math.BigDecimal
import java.util.*

@RunWith(JUnit4::class)
class RepositoryOperationTest {

    private lateinit var operationDao: OperationDao
    private lateinit var walletOperationDao: WalletOperationDao
    private lateinit var repositoryOperation: RepositoryOperation

    @Before
    fun setUp() {
        operationDao = Mockito.mock(OperationDao::class.java)
        walletOperationDao = Mockito.mock(WalletOperationDao::class.java)
        repositoryOperation = RepositoryOperationImpl(operationDao, walletOperationDao)
    }

    @Test
    fun getAll_verifyCallDaoGetAllAndHandlePeriodic() {

        val operationType = OperationType.COMPLETE

        val sumOperationMain = Money(BigDecimal.valueOf(6000.0), Currency.RUB)
        val sumCurrencyMain = Money(BigDecimal.valueOf(100.0), Currency.USD)
        val periodicDate = Date()
        val wallet = Wallet(1, "wallet", Money(BigDecimal(500.0), Currency.USD))
        val category = Category(1, CategoryType.OUTCOME, "Еда", "uri1")

        val periodicOperation = Operation(1, "comment", sumOperationMain, sumCurrencyMain, periodicDate,
                wallet, category, OperationType.PERIODIC, 1)
        val idleOperation = IdleOperation(1, wallet.walletId, "comment", sumCurrencyMain,
                sumOperationMain, Date(periodicDate.time + MS_IN_DAY), category.categoryId, OperationType.PERIODIC, 1)
        `when`(operationDao.qetAllPeriodicList()).thenReturn(listOf(periodicOperation))

        repositoryOperation.getAll(operationType)

        verify(operationDao).getAll(operationType)
        verify(operationDao).qetAllPeriodicList()
        verify(operationDao).update(idleOperation)
    }
}