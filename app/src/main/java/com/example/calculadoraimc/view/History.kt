package com.example.calculadoraimc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calculadoraimc.datasource.MeasurementDao
import com.example.calculadoraimc.ui.theme.Blue
import com.example.calculadoraimc.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController, dao: MeasurementDao) {
    val history by dao.getAllMeasurements().collectAsState(initial = emptyList())

    /** GEMINI - inıcio
     * Prompt: Este arquivo cuidará da listagem e formatação da data.
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico de Saúde", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue, titleContentColor = White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {
            items(history) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = formatDate(item.date),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        // IMC à Esquerda e CLASSIFICAÇÃO à Direita
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween // Joga os elementos para as pontas
                        ) {
                            Text(
                                text = "IMC: ${String.format("%.2f", item.imc)}",
                                fontSize = 24.sp, // Tamanho equilibrado para não quebrar
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )

                            Surface(
                                color = Blue.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = item.classification.uppercase(),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 12.sp, // Tamanho seguro para o lado direito
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Blue,
                                    maxLines = 1
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            thickness = 0.5.dp,
                            color = Color.LightGray
                        )

                        // Informações Adicionais (TMB e Peso Ideal)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Peso/Altura", fontSize = 11.sp, color = Color.Gray)
                                Text("${item.weight}kg / ${item.height.toInt()}cm", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Metabolismo (TMB)", fontSize = 11.sp, color = Color.Gray)
                                Text("${String.format("%.0f", item.tmb)} kcal", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Idade", fontSize = 11.sp, color = Color.Gray)
                                Text("${item.age} anos", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Peso Ideal", fontSize = 11.sp, color = Color.Gray)
                                Text("${String.format("%.1f", item.pesoIdeal)} kg", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                            }
                        }
                    }
                }
            }
        }
    }
    /** GEMINI - final */
}

fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}