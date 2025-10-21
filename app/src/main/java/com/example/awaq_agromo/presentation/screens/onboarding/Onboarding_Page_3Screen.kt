package com.example.awaq_agromo.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.presentation.component.buttons.PrimaryButton
import com.example.awaq_agromo.presentation.component.ui.ProgressBar
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.component.texts.TitleText
import com.example.awaq_agromo.presentation.component.ui.CustomProgressBar
import com.example.awaq_agromo.presentation.theme.AgromoTheme
import org.intellij.lang.annotations.JdkConstants

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Page_3Screen(
    onDashboardClick: () -> Unit = {}
) {

    var progress by remember { mutableFloatStateOf(1f) }

    AgromoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box() {
                ProgressBar(progress = 0f)
                CustomProgressBar(progress = progress)
            }

            Spacer(modifier = Modifier.height(10.dp))

            TitleText(
                text = "Seleccione sus cultivos",
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            SubtitleText(
                text = "Personalice tu experiencia añadiendo los cultivos que tiene en su granja",
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )

            Spacer(modifier = Modifier.height(500.dp))

            PrimaryButton(
                text = "Completar",
                onClick = onDashboardClick
            )
        }
    }
}