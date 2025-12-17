package com.example.calculadoraimc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.datasource.Measurement
import com.example.calculadoraimc.datasource.MeasurementDao
import com.example.calculadoraimc.ui.theme.Blue
import com.example.calculadoraimc.ui.theme.White
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Info

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, dao: MeasurementDao) {

    /** GEMINI - inıcio
     * Prompt: sumir o teclado assim que o botão de calcular for pressionado.
     */
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var age by remember { mutableStateOf("") }

    /** GEMINI - final */

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    var classification by remember { mutableStateOf("") }
    var textFieldError by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }

    /** GEMINI - inıcio
     * Prompt: Atualize a Home.kt para incluir o Botão de Histórico
     */
    val scope = rememberCoroutineScope()
    /** GEMINI - final */

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calculadora de Saúde") },
                actions = {
                    /** GEMINI - inıcio
                     * Prompt: Atualize a Home.kt para incluir o Botão de Histórico
                     */
                    IconButton(onClick = { navController.navigate("help") }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Ajuda",
                            tint = White
                        )
                    }
                    // Ícone de Histórico que já existia
                    IconButton(onClick = { navController.navigate("history") }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Histórico",
                            tint = White
                        )
                    }
                    /** GEMINI - final */
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xFFF5F5F5)) // Fundo levemente cinza para destacar os cards
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card de Entrada de Dados para evitar sobreposição
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dados Pessoais",
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = height,
                            onValueChange = { if (it.length <= 3) height = it },
                            label = { Text(text = "Altura (cm)") },
                            placeholder = { Text("Ex: 165", color = Color.Gray) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            isError = textFieldError
                        )

                        OutlinedTextField(
                            value = weight,
                            onValueChange = { if (it.length <= 7) weight = it },
                            label = { Text(text = "Peso (kg)") },
                            placeholder = { Text("Ex: 70.50", color = Color.Gray) },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            isError = textFieldError
                        )
                    }

                    OutlinedTextField(
                        value = age,
                        onValueChange = { if (it.length <= 3) age = it },
                        label = { Text("Idade") },
                        placeholder = { Text("Ex: 25", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedLabelColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        isError = textFieldError
                    )
                }
            }

            Button(
                onClick = {
                    /** GEMINI - inıcio (teclado sumir) */
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    /** GEMINI - final */

                    val errorMsg = validateInputs(height, weight, age)
                    if (errorMsg != null) {
                        resultMessage = errorMsg
                        textFieldError = true
                        showResults = false
                    } else {
                        textFieldError = false
                        val h = height.toDouble()
                        val w = weight.toDouble()
                        val a = age.toInt()

                        // Cálculos baseados nas fórmulas documentadas
                        val (imcVal, classif) = Calculations.calculateIMC(h, w)
                        val tmbVal = Calculations.calculateTMB(w, h, a)
                        val idealVal = Calculations.calculateIdealWeight(h)
                        val calVal = Calculations.calculateDailyCalories(tmbVal)

                        classification = classif
                        resultMessage = String.format("%.2f", imcVal)
                        showResults = true

                        scope.launch {
                            dao.insert(
                                Measurement(
                                    date = System.currentTimeMillis(),
                                    weight = w, height = h, age = a,
                                    imc = imcVal, classification = classif,
                                    tmb = tmbVal, pesoIdeal = idealVal, caloriasDiarias = calVal
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text(
                    text = "CALCULAR",
                    fontSize = 18.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Exibição dos resultados em cards individuais para cumprir as funcionalidades de saúde
            if (showResults) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CardResultadoIndividual("Seu IMC", resultMessage, classification, Blue)
                    CardResultadoIndividual(
                        "Peso Ideal",
                        "${
                            String.format(
                                "%.1f",
                                Calculations.calculateIdealWeight(height.toDouble())
                            )
                        } kg",
                        "Fórmula de Devine",
                        Color(0xFF4CAF50)
                    )
                    CardResultadoIndividual(
                        "Metabolismo (TMB)",
                        "${
                            String.format(
                                "%.0f",
                                Calculations.calculateTMB(
                                    weight.toDouble(),
                                    height.toDouble(),
                                    age.toInt()
                                )
                            )
                        } kcal",
                        "Mifflin-St Jeor",
                        Color(0xFFFF9800)
                    )
                }
            } else if (resultMessage.isNotEmpty() && textFieldError) {
                Text(
                    text = resultMessage,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Componente para exibir cada funcionalidade de saúde de forma legível
@Composable
fun CardResultadoIndividual(titulo: String, valor: String, info: String, corDestaque: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier
                .size(4.dp, 40.dp)
                .background(corDestaque))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(titulo, fontSize = 12.sp, color = Color.Gray)
                Text(valor, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(info, fontSize = 12.sp, color = corDestaque)
            }
        }
    }
}

/** GEMINI - inıcio
 * Prompt: vamos validar se os números fazem sentido
 */
fun validateInputs(height: String, weight: String, age: String): String? {
    val h = height.toDoubleOrNull()
    val w = weight.toDoubleOrNull()
    val a = age.toIntOrNull()

    return when {
        height.isEmpty() || weight.isEmpty() || age.isEmpty() -> "Preencha todos os campos"
        h == null || h !in 50.0..250.0 -> "Altura inválida (50-250cm)"
        w == null || w !in 10.0..500.0 -> "Peso inválido (10-500kg)"
        a == null || a !in 1..120 -> "Idade inválida (1-120 anos)"
        else -> null
    }
}

/** GEMINI - final */

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    /** GEMINI - inıcio
     * Prompt: Criamos um navController de teste
     */
    val navController = rememberNavController()
    /** GEMINI - final */
}