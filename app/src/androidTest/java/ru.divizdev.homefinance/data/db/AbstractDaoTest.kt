package ru.divizdev.homefinance.data.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.After
import org.junit.Before

abstract class AbstractDaoTest {
    protected lateinit var homeFinanceDatabase: HomeFinanceDatabase

    open fun initDb() {
        homeFinanceDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                HomeFinanceDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        homeFinanceDatabase.close()
    }
}