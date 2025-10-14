package com.example.awaq_agromo.presentation.screens.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.components.BottomBar
import com.example.awaq_agromo.components.NavItem
import com.example.awaq_agromo.components.TitleText
import com.example.awaq_agromo.components.profileComponents.InfoCard
import com.example.awaq_agromo.components.profileComponents.UserCard
// import com.example.awaq_agromo.data.informes.InformeData

/* private val sampleInformes: List<InformeData> = listOf(
    InformeData("12 sept", "Informe integral", "Atender", Color.Red, R.drawable.image_ph),
    InformeData("15 sept", "Revisión hortalizas", "En curso", Color.Blue, R.drawable.image_ph),
    InformeData("20 sept", "Detección de plaga", "Urgente", Color.Red, R.drawable.planta_de_pimientos),
    InformeData("25 sept", "Abono orgánico", "Pendiente", Color.Gray, R.drawable.image_ph),
    InformeData("28 sept", "Ajuste de riego", "Completado", Color.Green, R.drawable.planta_de_pimientos),
    InformeData("01 oct", "Mantenimiento", "En curso", Color.Blue, R.drawable.planta_de_pimientos),
    InformeData("05 oct", "Fertilización", "Pendiente", Color.Gray, R.drawable.image_ph),
)
*/
@Preview(showSystemUi = true)
@Composable
fun PerfilScreen(
    onDashboardClick: () -> Unit = {},
    onMonitoringClick: () -> Unit = {},
    onInformePlantaClick: () -> Unit = {},
    onCommunityClick: () -> Unit = {} // Desavilitado, para implemetacion futura
) {
    val colorBorde = Color(0xFF344E18)
    val colorTexto = colorBorde
    val colorIconos = colorBorde

    var selectedRoute by remember { mutableStateOf(NavItem.Inicio.route) }

    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {} // No se que se deberia de poder hacerse...
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Perfil",
                        tint = colorIconos,
                        modifier = Modifier.size(24.dp)
                    )
                }

                TitleText(
                    text = ("Perfil"),
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = colorIconos,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(modifier = Modifier.height(30.dp))

            UserCard(
                textColor = colorTexto,
                borderColor = colorBorde,
                nombreUsuario = "Nombre de Usuario",
                ubicacion = "Lugar del Usuario",
                imagenUsuario = R.drawable.chiili
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleText(
                    text = ("Mis informes"),
                    color = Color.Black,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                /*
                items(sampleInformes) { informe ->
                    InfoCard(
                        date = informe.date,
                        title = informe.title,
                        status = informe.status,
                        borderColor = colorBorde,
                        statusColor = informe.statusColor,
                        imagen = informe.imagen,
                        onMoreInformationClick = onInformePlantaClick
                    )
                }
            }*/
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
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
}
