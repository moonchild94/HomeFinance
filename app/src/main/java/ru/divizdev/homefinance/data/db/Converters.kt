package ru.divizdev.homefinance.data.db

import android.arch.persistence.room.TypeConverter
import ru.divizdev.homefinance.entities.Currency
import ru.divizdev.homefinance.entities.OperationType
import java.math.BigDecimal
import java.util.*

class Converters {
    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun bigDecimalFromString(value: String?): BigDecimal? {
        return value?.toBigDecimal()
    }

    @TypeConverter
    fun bigDecimalToString(bigDecimal: BigDecimal?): String? {
        return bigDecimal?.toString()
    }

    @TypeConverter
    fun operationTypeFromOrdinal(value: Int?): OperationType? {
        return if (value == null) null else OperationType.values()[value]
    }

    @TypeConverter
    fun operationTypeToOrdinal(operationType: OperationType?): Int? {
        return operationType?.ordinal
    }

    @TypeConverter
    fun currencyFromOrdinal(value: Int?): Currency? {
        return if (value == null) null else Currency.values()[value]
    }

    @TypeConverter
    fun currencyToOrdinal(currency: Currency?): Int? {
        return currency?.ordinal
    }
}