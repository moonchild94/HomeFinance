package ru.divizdev.homefinance.presentation.operationslist.presenter

import ru.divizdev.homefinance.entities.OperationType

data class OperationFilter(val walletPosition: Int, val operationType: OperationType)