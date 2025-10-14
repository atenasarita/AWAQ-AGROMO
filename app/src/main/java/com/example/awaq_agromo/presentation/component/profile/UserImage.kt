package com.example.awaq_agromo.presentation.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    UserPhoto: Int = R.drawable.user_photo,
    borderColor: Color = Color(0xFF344E18)
){
    Image(
        painter = painterResource(id = UserPhoto),
        contentDescription = "User Photo",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .border(3.dp, borderColor, RoundedCornerShape(60.dp))
            .clip(CircleShape)
    )
}