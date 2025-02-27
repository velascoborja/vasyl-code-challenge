package com.vasyl.code.vasylchallenge.presentation.utils

import com.vasyl.code.vasylchallenge.BuildConfig

enum class BuildFlavor(val value: String) {
    CLIENT_1("client1"),
    CLIENT_2("client2");

    companion object {
        @JvmStatic
        fun fromValue(value: String): BuildFlavor {
            return entries.find { it.value == value } ?: CLIENT_1
        }
    }
}

fun getBuildFlavor(): BuildFlavor {
    return BuildFlavor.fromValue(BuildConfig.FLAVOR)
}