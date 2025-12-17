package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculadoraimc.datasource.AppDatabase
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.view.HistoryScreen
import com.example.calculadoraimc.view.Home
import com.example.calculadoraimc.view.HelpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CalculadoraIMCTheme {
                val navController = rememberNavController()

                val db = AppDatabase.getDatabase(applicationContext)
                val dao = db.measurementDao()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    // Rota da tela principal
                    composable("home") {
                        Home(navController = navController, dao = dao)
                    }

                    // Rota da tela de histórico
                    composable("history") {
                        HistoryScreen(navController = navController, dao = dao)
                    }

                    // Rota da tela de ajuda (Glossário de Saúde)
                    composable("help") {
                        HelpScreen(navController = navController)
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
    object Help : Screen("help")
}