package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.divizdev.homefinance.entities.Money

/**
 * Кошелек.
 */
@Entity
@Parcelize
data class Wallet(
        @PrimaryKey(autoGenerate = true) val walletId: Int = 0,
        var walletName: String,
        @Embedded
        var balance: Money) : Parcelable