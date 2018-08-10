package ru.divizdev.homefinance.data.db

import android.support.test.runner.AndroidJUnit4
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.divizdev.homefinance.data.db.dao.WalletDao
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.Money
import ru.divizdev.homefinance.entities.Wallet
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

private const val WALLET_NAME = "Счет1"
private const val WALLET_AMOUNT = 12.90
private const val WALLET_ID = 1

@RunWith(AndroidJUnit4::class)
class WalletDaoTest : AbstractDaoTest() {

    private lateinit var walletDao: WalletDao

    @Before
    override fun initDb() {
        super.initDb()
        walletDao = homeFinanceDatabase.getWalletDao()
    }

    @Test
    fun newWallet_getAll_returnsListWithTheWallet() {
        val wallet = Wallet(walletName = WALLET_NAME, balance = Money(BigDecimal.valueOf(WALLET_AMOUNT), Currency.RUB))
        walletDao.insert(wallet)

        walletDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 1 && wallet.copy(walletId = WALLET_ID) == it[0]
                }
    }

    @Test
    fun newWallet_getById_returnsExpectedWallet() {
        val wallet = Wallet(walletName = WALLET_NAME, balance = Money(BigDecimal.valueOf(WALLET_AMOUNT), Currency.RUB))
        walletDao.insert(wallet)

        walletDao.getById(WALLET_ID)
                .subscribeOn(Schedulers.newThread())
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it == wallet.copy(walletId = WALLET_ID)
                }
    }

    @Test
    fun emptyDb_getById_returnsEmpty() {
        walletDao.getById(WALLET_ID)
                .subscribeOn(Schedulers.newThread())
                .test()
                .assertEmpty()
    }

    @Test
    fun emptyDb_getAll_returnsEmpty() {
        walletDao.getAll()
                .subscribeOn(Schedulers.newThread())
                .test()
                .assertEmpty()
    }
}