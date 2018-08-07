package ru.divizdev.homefinance.data.mapper

import ru.divizdev.homefinance.entities.IdleOperation
import ru.divizdev.homefinance.entities.Operation

object OperationMapper {

    fun mapOperationToIdleOperation(operation: Operation) : IdleOperation {
        val id = operation.operationId
        val walletId = operation.wallet.walletId
        val comment = operation.comment
        val sumCurrencyMain = operation.sumCurrencyMain
        val sumCurrencyOperation = operation.sumCurrencyOperation
        val date = operation.date
        val categoryId = operation.category.categoryId
        val period = operation.period
        val operationType = operation.operationType

        return IdleOperation(id, walletId, comment, sumCurrencyMain, sumCurrencyOperation, date, categoryId, operationType, period)
    }
}