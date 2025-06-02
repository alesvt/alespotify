package com.alespotify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alespotify.model.Cancion
import com.alespotify.shared.ApiService
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.QueueViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogoBuscaCanciones(
    apiService: ApiService,
    queueViewModel: QueueViewModel,
    onDismiss: () -> Unit
) {
    var queryBusqueda by remember { mutableStateOf("") }
    var resultadosBusqueda by remember { mutableStateOf<List<Cancion>?>(null) }
    val scope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }



    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            modifier = Modifier
                .width(600.dp)
                .height(600.dp)
                .background(MyColors.background, MaterialTheme.shapes.medium)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Buscar Canciones",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )

            TextField(
                value = queryBusqueda,
                onValueChange = { newText ->
                    println(newText)
                    queryBusqueda = newText
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        delay(300)
                        if (newText.isNotBlank()) {
                            try {
                                resultadosBusqueda = apiService.getSongsByName(newText)
                                println(resultadosBusqueda)
                            } catch (e: Exception) {
                                println("Error searching songs: ${e.message}")
                                resultadosBusqueda = emptyList()
                            }
                        } else {
                            resultadosBusqueda = emptyList()
                        }
                    }
                },
                label = { Text("Nombre...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    backgroundColor = MaterialTheme.colors.background,
                    cursorColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (resultadosBusqueda?.isEmpty() == true && queryBusqueda.isNotBlank()) {
                Text(
                    text = "No se encontraron resultados.",
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else if (resultadosBusqueda?.isEmpty() == true && queryBusqueda.isBlank()) {
                Text(
                    text = "Empieza a escribir para buscar canciones...",
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(resultadosBusqueda?: emptyList()) { cancion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        queueViewModel.playSong(cancion)
                                        onDismiss()
                                    }
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = cancion.name,
                                    color = MaterialTheme.colors.onSurface,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                                cancion.artists?.let { artist ->
                                    Text(
                                        text = artist[0].name,
                                        color = MyColors.secondary.copy(alpha = 0.6f),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        Divider(color = MyColors.secondary.copy(alpha = 0.1f))
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = MyColors.primary)
            ) {
                Text("Cerrar", color = Color.White)
            }
        }
    }

}


