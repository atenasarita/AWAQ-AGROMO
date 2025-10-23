package com.example.awaq_agromo.presentation.component.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.presentation.theme.Primary900
import com.example.awaq_agromo.presentation.theme.Primary300
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.AutoGraph

sealed class NavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Inicio : NavItem("inicio", "Inicio", Icons.Default.Home)
    data object Monitoreo : NavItem("monitoreo", "Monitoreo", Icons.Filled.AutoGraph)
    data object Comunidad : NavItem("comunidad", "Comunidad", Icons.Filled.Face)
    data object Perfil : NavItem("perfil", "Perfil", Icons.Filled.Person)
}

val items = listOf(NavItem.Inicio, NavItem.Monitoreo, NavItem.Comunidad, NavItem.Perfil)

@Composable
fun BottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    val circleSize = 64.dp
    val bottomBarHeight = 80.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomBarHeight)
            .background(Primary300, RoundedCornerShape(32.dp))
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        items.forEach { item ->
            val isSelected = item.route == selectedRoute

            // animacion del desplazamiento
            val offsetY: Dp by animateDpAsState(
                targetValue = if (isSelected) -25.dp else 0.dp,
                animationSpec = tween(durationMillis = 300), label = "itemOffsetY"
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onNavigate(item.route) }
                    .padding(bottom = if (isSelected) 0.dp else 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = offsetY)
                        .size(if (isSelected) circleSize else 42.dp)
                        .clip(CircleShape)
                        .background(Primary300)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                        tint = Primary900
                    )
                }


                Text(
                    text = item.label,
                    modifier = Modifier
                        .offset(y = offsetY),
                    color = Primary900,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}


@Preview()
@Composable
fun AwaqBottomNavBarInteractiveMockup() {
    var selectedRoute by remember { mutableStateOf(NavItem.Inicio.route) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomBar(
            selectedRoute = selectedRoute,
            onNavigate = { newRoute ->
                selectedRoute = newRoute
            }
        )
    }
}