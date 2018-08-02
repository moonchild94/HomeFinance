package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.divizdev.homefinance.entities.Money

/**
 * Кошелек.
 */
@Entity
data class Wallet(
        @PrimaryKey(autoGenerate = true) val walletId: Int? = null,
        val name: String,
        @Embedded
        var balance: Money)