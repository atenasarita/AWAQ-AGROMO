package com.example.awaq_agromo.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.awaq_agromo.components.PrimaryButton
import com.example.awaq_agromo.components.ProgressBar
import com.example.awaq_agromo.components.SubtitleText
import com.example.awaq_agromo.components.TitleText

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Page_3Screen(
    onDashboardClick: () -> Unit = {}
){

    var progress by remember { mutableFloatStateOf(1f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProgressBar(progress = progress)

        Spacer(modifier = Modifier.height(20.dp))

        TitleText(
            text = "Seleccione sus cultivos"
        )
        SubtitleText(
            text = "Personalice tu experiencia añadiendo los cultivos que tiene en su granja"
        )

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = "Completar",
            onClick = {onDashboardClick}
        )
    }
}