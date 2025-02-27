package com.vasyl.code.vasylchallenge.presentation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Procedures : Screen()

    @Serializable
    data object FavouriteProcedures : Screen()
}