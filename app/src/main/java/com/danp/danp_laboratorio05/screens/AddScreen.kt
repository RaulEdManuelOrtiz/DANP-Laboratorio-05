package com.danp.danp_laboratorio05.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.danp.danp_laboratorio05.CarViewModel
import com.danp.danp_laboratorio05.data.Car


@Composable
fun AddScreen(
    navController: NavController,
    carViewModel: CarViewModel,
    onSetAppTitle: (String) -> Unit,
    onShowFab: (Boolean) -> Unit,
    onCarAdded: (Car) -> Unit
) {
    var carPlate by remember { mutableStateOf("") }
    var carColor by remember { mutableStateOf("") }
    var carBrand by remember { mutableStateOf("") }
    var carModel by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onSetAppTitle("Agregar un vehiculo")
        onShowFab(false)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = carPlate,
            onValueChange = { carPlate = it },
            label = { Text("Placa") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = carColor,
            onValueChange = { carColor = it },
            label = { Text("Color") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = carBrand,
            onValueChange = { carBrand = it },
            label = { Text("Marca") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = carModel,
            onValueChange = { carModel = it },
            label = { Text("Modelo") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                if (carViewModel.isItemValid(carPlate, carColor, carBrand, carModel)) {
                    onCarAdded(
                        Car(
                            0,
                            carPlate.trim(),
                            carColor.trim(),
                            carBrand.trim(),
                            carModel.trim(),
                        )
                    )
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Hay un campo vacio!!", Toast.LENGTH_LONG)
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF093980))
        ) {
            Text(text = "Agregar vehiculo", color = Color.White)
        }
    }
}