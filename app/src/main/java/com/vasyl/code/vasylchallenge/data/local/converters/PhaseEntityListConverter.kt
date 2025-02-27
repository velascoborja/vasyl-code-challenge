package com.vasyl.code.vasylchallenge.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vasyl.code.vasylchallenge.data.local.model.PhaseEntity

class PhaseEntityListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<PhaseEntity>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String?): List<PhaseEntity>? {
        val type = object : TypeToken<List<PhaseEntity>>() {}.type
        return gson.fromJson(data, type)
    }
}