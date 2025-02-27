package com.vasyl.code.vasylchallenge.presentation.utils

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.text.ParseException
import java.text.SimpleDateFormat

class DateTimeConverterTest {

    @Test
    fun `formatDate should correctly format a valid date`() {
        val inputDate = "2024-02-27T15:30:45.123"
        val expectedOutput = "27/02/2024"

        val result = DateTimeConverter.formatDate(inputDate)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `formatDate should return empty string for invalid date format`() {
        val invalidDate = "2024/02/27 15:30:45"

        val result = DateTimeConverter.formatDate(invalidDate)

        assertEquals("", result) // Expected: empty string due to parsing failure
    }

    @Test
    fun `formatDate should return empty string for empty input`() {
        val result = DateTimeConverter.formatDate("")

        assertEquals("", result)
    }

    @Test
    fun `formatDate should throw ParseException for completely invalid input`() {
        val invalidDate = "invalid-date"

        assertFailsWith<ParseException> {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(invalidDate)
        }
    }
}
