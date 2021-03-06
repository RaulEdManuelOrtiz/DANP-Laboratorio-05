package com.danp.danp_laboratorio05.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.danp.danp_laboratorio05.CarViewModel
import com.danp.danp_laboratorio05.data.Car

@Composable
fun DetailsScreen(
    carId: String?,
    navController: NavController,
    carViewModel: CarViewModel,
    onSetAppTitle: (String) -> Unit,
    onShowFab: (Boolean) -> Unit,
    onCarDeleted: (Car) -> Unit
) {

    LaunchedEffect(Unit) {
        onSetAppTitle("Detalles del vehiculo")
        onShowFab(false)
    }

    val receivedCar = carViewModel.getItem(carId!!.toInt()).observeAsState()
    val car = receivedCar.value ?: Car(0, "", "","", "")

    val showDialog = remember { mutableStateOf(false) }
    val deleteConfirmed = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowConfirmationDialog(
            title = "Eliminar vehiculo?",
            text = "¿Esta seguro que desea eliminar este vehiculo?",
            onResponse = {
                deleteConfirmed.value = it
                showDialog.value = false
            }
        )
    }

    if (deleteConfirmed.value) {
        onCarDeleted(car)
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
        }
        deleteConfirmed.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Card(
                elevation = 10.dp,
                modifier =
                Modifier
                    .padding(10.dp)
                    .height(70.dp)
                    .width(300.dp)
                ,
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Text(
                    text =  car.carPlate,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF60708F)
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(
                    start = 30.dp,
                    top = 15.dp,
                    end = 30.dp,
                    bottom = 15.dp,
                )
        ) {
            Text(
                text = "Marca",
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(100.dp),
                color = Color(0xFF1873B9)
            )
            Text(
                text = car.carBrand,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF60708F)

            )
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(
                    start = 30.dp,
                    top = 15.dp,
                    end = 30.dp,
                    bottom = 15.dp,
                ),
        ) {
            Text(
                text = "Color",
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(100.dp),
                color = Color(0xFF1873B9)
            )
            Text(
                text = car.carColor,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF60708F)

            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(
                    start = 30.dp,
                    top = 15.dp,
                    end = 30.dp,
                    bottom = 15.dp,
                )
        ) {
            Text(
                text = "Modelo",
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(100.dp),
                color = Color(0xFF1873B9)
            )
            Text(
                text = car.carModel,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF60708F)

            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate("edit/${car.id}") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF093980))

        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)

            )
            Text("Editar vehiculo", modifier = Modifier.padding(start = 8.dp), color = Color.White)
        }

        OutlinedButton(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color(0xFF093980),
                modifier = Modifier.size(16.dp)
            )
            Text("Eliminar Vehiculo", modifier = Modifier.padding(start = 8.dp), color = Color(0xFF093980) )
        }

    }
}

@Composable
fun ShowConfirmationDialog(title: String, text: String, onResponse: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { onResponse(false) },
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = { onResponse(true) }) {
                Text("Si", color = Color(0xFF093980))
            }
        },
        dismissButton = {
            TextButton(onClick = { onResponse(false) }) {
                Text("No", color = Color(0xFF093980))
            }
        }
    )
}