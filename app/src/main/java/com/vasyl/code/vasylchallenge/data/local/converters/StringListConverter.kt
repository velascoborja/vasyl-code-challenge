package com.vasyl.code.vasylchallenge.data.local.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(data: String?): List<String>? {
        return data?.split(",")?.map { it.trim() }
    }
}