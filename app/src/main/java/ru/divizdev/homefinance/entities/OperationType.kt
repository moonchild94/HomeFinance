package ru.divizdev.homefinance.entities

import ru.divizdev.homefinance.R

enum class OperationType(val stringId: Int) {
    PERIODIC(R.string.periodic_type),
    TEMPLATE(R.string.template_type),
    COMPLETE(R.string.complete_type)
}