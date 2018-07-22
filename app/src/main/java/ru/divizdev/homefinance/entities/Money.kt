package ru.divizdev.homefinance.entities

import java.math.BigDecimal
import java.math.RoundingMode

class Money(_value: BigDecimal, val currency: Currency)  {
    val value:BigDecimal = _value.setScale(2, RoundingMode.HALF_UP)
}
