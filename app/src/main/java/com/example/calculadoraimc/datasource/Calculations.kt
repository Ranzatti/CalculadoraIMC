package com.example.calculadoraimc.datasource

object Calculations {

    /** GEMINI - inıcio
     * Prompt:
     * Calcula o IMC e retorna uma interpretação textual.
     */
    fun calculateIMC(height: Double, weight: Double): Pair<Double, String> {
        val imc = weight / ((height / 100) * (height / 100))
        val classification = when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5..24.9 -> "Peso Normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc in 30.0..34.9 -> "Obesidade (Grau 1)"
            imc in 35.0..39.9 -> "Obesidade Severa (Grau 2)"
            else -> "Obesidade Mórbida (Grau 3)"
        }
        return Pair(imc, classification)
    }

    /** GEMINI - inıcio
     * Prompt:
     * Taxa Metabólica Basal (TMB) - Fórmula de Mifflin-St Jeor.
     * Homens: (10 * peso) + (6.25 * altura) - (5 * idade) + 5
     */
    fun calculateTMB(weight: Double, height: Double, age: Int): Double {
        return (10 * weight) + (6.25 * height) - (5 * age) + 5
    }

    /** GEMINI - inıcio
     * Prompt:
     * Peso Ideal - Fórmula de Devine (1974).
     * Referência: 50kg + 2.3kg para cada polegada acima de 5 pés (152.4 cm).
     */
    fun calculateIdealWeight(height: Double): Double {
        val inchesAbove = (height - 152.4) / 2.54
        return if (inchesAbove > 0) 50 + (2.3 * inchesAbove) else 50.0
    }

    /** GEMINI - inıcio
     * Prompt:
     * Estimativa de necessidade calórica diária (Fator de atividade moderada: 1.55).
     */
    fun calculateDailyCalories(tmb: Double): Double {
        return tmb * 1.55
    }

    /** GEMINI - final */
}