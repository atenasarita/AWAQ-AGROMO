package com.example.awaq_agromo.components.profileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.components.SubtitleText
import com.example.awaq_agromo.components.TitleText

@Composable
fun UserCard(
    textColor: Color = Color.Black,
    borderColor: Color = Color(0xFF344E18),
    imagenUsuario: Int = R.drawable.user_photo,
    nombreUsuario: String = "Nombre de Usuario",
    ubicacion: String = "Lugar del Usuario"
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .background(
                color = Color.White
            )
            .border(
                width = 1.dp,borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserImage(UserPhoto = imagenUsuario,
            modifier = Modifier.padding(start = 10.dp,end = 10.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            TitleText(text = nombreUsuario, color = textColor)
            SubtitleText(text = ubicacion)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun mostrarUserCard() {
    UserCard()
}