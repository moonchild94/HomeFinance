package ru.divizdev.homefinance.entities

import android.arch.persistence.room.Embedded
import java.math.BigDecimal

/**
 * Статистика операций для заданной категории.
 */
class OperationStatistics(@Embedded
                         val category: Category,
                          val amount: BigDecimal)