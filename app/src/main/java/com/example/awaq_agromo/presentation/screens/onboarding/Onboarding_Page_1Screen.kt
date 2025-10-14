package com.example.awaq_agromo.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.presentation.component.buttons.PrimaryButton
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.component.texts.TitleText

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Page_1Screen(
    onNextOnboardingPage2: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleText(
            text = "¡Bienvenido a Agromo!"
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    Color(0xFF829500),
                    shape = MaterialTheme.shapes.medium)
        ) {
            SubtitleText(
                text = "Mantenga todo los parámetros de seguimiento de sus cultivos al día y colsulte el saber local con la comunidad",
                modifier = Modifier.padding(10.dp),
                color = Color.White

            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.onboarding_page_1),
            contentDescription = "Granjera cosechando",
            modifier = Modifier.size(120.dp).fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = "Quiero registrarme",
            onClick = onNextOnboardingPage2
        )
    }
}