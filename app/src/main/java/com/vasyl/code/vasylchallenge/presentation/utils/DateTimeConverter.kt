package com.vasyl.code.vasylchallenge.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeConverter {

    fun formatDate(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date: Date? = inputFormatter.parse(inputDate)
            date?.let { outputFormatter.format(it) } ?: ""
        } catch (t: Throwable) {
            ""
        }
    }
}