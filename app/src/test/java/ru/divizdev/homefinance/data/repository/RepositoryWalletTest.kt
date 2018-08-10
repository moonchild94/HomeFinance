package ru.divizdev.homefinance.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Wallet
import java.math.BigDecimal

class RepositoryWalletTest {

    private lateinit var walletDao: WalletDao
    private lateinit var repositoryWallet: RepositoryWallet
    private val wallet = Wallet(1, "wallet", Money(BigDecimal.valueOf(100.00), Currency.USD))

    @Before
    fun setUp() {
        walletDao = Mockito.mock(WalletDao::class.java)
        repositoryWallet = RepositoryWalletImpl(walletDao)
    }

    @Test
    fun update_verifyCallDaoUpdate() {

        repositoryWallet.update(wallet)

        verify(walletDao).update(wallet)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun delete_verifyCallDaoDelete() {

        repositoryWallet.delete(wallet)

        verify(walletDao).delete(wallet)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun add_verifyCallDaoInsert() {

        repositoryWallet.add(wallet)

        verify(walletDao).insert(wallet)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun getById_verifyCallDaoGetById() {

        repositoryWallet.getById(1)

        verify(walletDao).getById(1)
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun getAll_verifyCallDaoGetAll() {

        repositoryWallet.getAll()

        verify(walletDao).getAll()
        verifyNoMoreInteractions(walletDao)
    }
}