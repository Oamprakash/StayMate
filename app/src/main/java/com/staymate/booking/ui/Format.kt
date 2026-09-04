package com.staymate.booking.ui

import java.text.NumberFormat
import java.util.Locale

private val rupeeFormat: NumberFormat = NumberFormat.getIntegerInstance(Locale("en", "IN"))

fun rupees(amount: Int): String = "₹${rupeeFormat.format(amount)}"
