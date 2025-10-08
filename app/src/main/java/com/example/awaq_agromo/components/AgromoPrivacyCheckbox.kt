package com.example.awaq_agromo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.ui.theme.Black
import com.example.awaq_agromo.ui.theme.PrincipalPrimary

@Composable
fun AgromoPrivacyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = PrincipalPrimary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(4.dp))

        val annotatedString = buildAnnotatedString {
            append("Acepto el procesamiento de mis datos de acuerdo con la ")
            pushStringAnnotation(tag = "POLICY", annotation = "policy_link")
            withStyle(
                style = SpanStyle(
                    color = PrincipalPrimary,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Política de Privacidad.")
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "POLICY", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onLinkClick()
                    }
            },
            style = MaterialTheme.typography.bodyMedium.copy(color = Black)
        )
    }
}