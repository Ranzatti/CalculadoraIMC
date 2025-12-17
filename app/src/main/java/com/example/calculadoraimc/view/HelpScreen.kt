package com.example.calculadoraimc.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calculadoraimc.ui.theme.Blue
import com.example.calculadoraimc.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Informações de Saúde") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue, titleContentColor = White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HelpSection(
                title = "1. Taxa Metabólica Basal (TMB)",
                formula = "Fórmula: (10 * peso) + (6.25 * altura) - (5 * idade) + 5",
                description = "Representa o mínimo de calorias que seu corpo precisa para manter as funções vitais em repouso."
            )

            HelpSection(
                title = "2. Peso Ideal",
                formula = "Fórmula de Devine: 50kg + 2.3kg por polegada acima de 152.4cm",
                description = "Uma estimativa clássica do peso saudável com base na sua altura e estrutura óssea média."
            )

            HelpSection(
                title = "3. Necessidade Calórica",
                formula = "Fórmula: TMB * Fator de Atividade (1.55)",
                description = "Estimativa de quantas calorias você consome por dia considerando atividades físicas moderadas."
            )

            HelpSection(
                title = "4. IMC (Índice de Massa Corporal)",
                formula = "Fórmula: Peso / (Altura * Altura)",
                description = "Padrão da OMS para classificar o peso. \n• < 18.5: Abaixo do peso\n• 18.5 - 24.9: Peso Normal\n• 25 - 29.9: Sobrepeso\n• > 30: Obesidade"
            )
        }
    }
}

@Composable
fun HelpSection(title: String, formula: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = Blue, fontSize = 18.sp)
            Text(formula, fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
            Text(description, fontSize = 14.sp)
        }
    }
}