package com.danp.danp_laboratorio05.screens.person

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

import com.danp.danp_laboratorio05.PersonViewModel
import com.danp.danp_laboratorio05.data.Person


@Composable
fun AddScreen(
    navController: NavController,
    personViewModel: PersonViewModel,
    onSetAppTitle: (String) -> Unit,
    onShowFab: (Boolean, Boolean) -> Unit,
    onPersonAdded: (Person) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var identification by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onSetAppTitle("Agregar una persona")
        onShowFab(false, false)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Apellido") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = identification,
            onValueChange = { identification = it },
            label = { Text("DNI") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Telefono") },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                if (personViewModel.isItemValid(name, surname, identification, phoneNumber)) {
                    onPersonAdded(
                        Person(
                            0,
                            name.trim(),
                            surname.trim(),
                            identification.trim(),
                            phoneNumber.trim(),
                        )
                    )
                    navController.navigate("personHome") {
                        popUpTo("personHome") { inclusive = true }
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
            Text(text = "Agregar persona", color = Color.White)
        }
    }
}