package com.example.awaq_agromo.presentation.screens.perfil


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.awaq_agromo.presentation.component.ui.NavItem
import com.example.awaq_agromo.presentation.component.profile.InfoCard
import com.example.awaq_agromo.presentation.component.profile.UserCard
import com.example.awaq_agromo.presentation.component.texts.TitleText
import com.example.awaq_agromo.presentation.model.InformeData
import com.example.awaq_agromo.presentation.screens.dashboard.sampleInformesRecientes

val sampleInformes: List<InformeData> = listOf(
    InformeData("12 sept", "Informe integral", "Atender",  Color.Red, R.drawable.image_ph, "Calabaza"),
    InformeData("15 sept", "Revisión hortalizas", "En curso", Color.Blue, R.drawable.image_ph, "Pimiento"),
    InformeData("20 sept", "Detección de plaga", "Urgente", Color.Red, R.drawable.planta_de_pimientos, "Calabaza"),
    InformeData("25 sept", "Abono orgánico", "Pendiente", Color.Gray, R.drawable.image_ph, "Berenjena"),
    InformeData("28 sept", "Ajuste de riego", "Completado", Color.Green, R.drawable.planta_de_pimientos, "Aceituna"),
    InformeData("01 oct", "Mantenimiento", "En curso", Color.Blue, R.drawable.planta_de_pimientos, "Chili"),
    InformeData("05 oct", "Fertilización", "Pendiente", Color.Gray, R.drawable.image_ph, "Tomate"),
)

@Preview(showSystemUi = true)
@Composable
fun PerfilScreen() {
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
                    .weight(1f)
                    .padding(bottom = 50.dp),

                contentPadding = PaddingValues(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                items(sampleInformesRecientes) { informe ->
                    InfoCard(
                        informe = informe,
                        onMoreInformationClick = {
                             // TODO
                        }
                    )
                }
            }
        }
    }
}