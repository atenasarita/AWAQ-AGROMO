package com.example.awaq_agromo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.ui.theme.Neutral400
import com.example.awaq_agromo.ui.theme.PrincipalNeutral

@Composable
fun AgromoSecondaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = PrincipalNeutral,
            contentColor = Neutral400.copy(alpha = if (enabled) 1f else 0.5f),
        ),
        border = null,
        modifier = modifier.fillMaxWidth().height(56.dp)
    ) {
        Text(text.uppercase(), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AgromoSecondaryButtonPreview(){
    AgromoSecondaryButton(
        text = "REGISTRARME",
        onClick = { },
    )
}