package com.example.awaq_agromo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.awaq_agromo.navigation.AppNavigation
import com.example.awaq_agromo.presentation.theme.AgromoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgromoTheme {
                val navController = rememberNavController()

                AppNavigation(navController = navController)
            }
        }
    }
}
