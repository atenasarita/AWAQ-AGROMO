package com.example.awaq_agromo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.ui.theme.Neutral400
import com.example.awaq_agromo.ui.theme.Primary900


// --- Botón Principal (Verde Oscuro - Estilo Activo) ---
@Composable
fun AgromoPrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary900, // Usando color principal
            contentColor = Color.White,
            disabledContainerColor = Neutral400, // Usando color principal
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        modifier = modifier.fillMaxWidth().height(56.dp)
    ) {
        Text(text.uppercase(), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AgromoPrimaryButtonPreview(){
    AgromoPrimaryButton(
        text = "REGISTRARME",
        onClick = { /* No hacer nada o mostrar error */ },
        enabled = false
    )
}