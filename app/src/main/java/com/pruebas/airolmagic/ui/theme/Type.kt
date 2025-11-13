package com.pruebas.airolmagic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R

val MedievalSharp = FontFamily(
    Font(R.font.medievalsharp)
)

val Lora = FontFamily(
    // --- Estilos Normales ---
    Font(R.font.lora, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.lora_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.lora_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.lora_bold, FontWeight.Bold, FontStyle.Normal),

    // --- Estilos Itálicos ---
    Font(R.font.lora_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.lora_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.lora_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.lora_bold_italic, FontWeight.Bold, FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = MedievalSharp,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)