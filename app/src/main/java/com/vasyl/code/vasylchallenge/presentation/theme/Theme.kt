package com.vasyl.code.vasylchallenge.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.vasyl.code.vasylchallenge.presentation.utils.BuildFlavor
import com.vasyl.code.vasylchallenge.presentation.utils.getBuildFlavor

private val DarkColorSchemeClient1 = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorSchemeClient1 = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val DarkColorSchemeClient2 = darkColorScheme(
    primary = Color.Green,
    secondary = Color.Green.copy(alpha = 0.8f),
    tertiary = Pink80
)

private val LightColorSchemeClient2 = lightColorScheme(
    primary = Color.Red,
    secondary = Color.Red.copy(alpha = 0.8f),
    tertiary = Pink40
)

@Composable
fun VasylChallengeTheme(
    flavor: BuildFlavor = getBuildFlavor(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> when(flavor){
            BuildFlavor.CLIENT_1->DarkColorSchemeClient1
            BuildFlavor.CLIENT_2-> DarkColorSchemeClient2
        }
        else ->  when(flavor){
            BuildFlavor.CLIENT_1-> LightColorSchemeClient1
            BuildFlavor.CLIENT_2-> LightColorSchemeClient2
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}