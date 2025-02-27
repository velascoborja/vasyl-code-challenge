package com.vasyl.code.vasylchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vasyl.code.vasylchallenge.presentation.procedures.favourite.FavouriteProceduresRoute
import com.vasyl.code.vasylchallenge.presentation.procedures.list.ProceduresListRoute
import com.vasyl.code.vasylchallenge.presentation.theme.VasylChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VasylChallengeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = Screen.Procedures
                ) {
                    composable<Screen.Procedures> {
                        ProceduresListRoute(navController)
                    }

                    composable<Screen.FavouriteProcedures> {
                        FavouriteProceduresRoute(navController)
                    }
                }
            }
        }
    }
}
