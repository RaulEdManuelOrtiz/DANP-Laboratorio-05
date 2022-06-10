package com.danp.danp_laboratorio05.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danp.danp_laboratorio05.CarViewModel
import com.danp.danp_laboratorio05.data.Car
import com.danp.danp_laboratorio05.R


@Composable
fun HomeScreen(
    navController: NavController,
    carViewModel: CarViewModel,
    onSetAppTitle: (String) -> Unit,
    onShowFab: (Boolean) -> Unit
) {
    val appTitle = stringResource(id = R.string.app_name)
    val cars: List<Car> by carViewModel.allItems.observeAsState(listOf())

    LaunchedEffect(Unit) {
        onSetAppTitle(appTitle)
        onShowFab(true)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            item { ItemHeaderLayout() }

            items(cars) { car ->
                ItemLayout(car, navController)
            }
        }
    }
}

@Composable
fun ItemHeaderLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF093980))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Placas",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Modelos",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Marcas",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ItemLayout(car: Car, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F4FB))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { navController.navigate("details/${car.id}") }

    ) {
        Text(car.carPlate, modifier = Modifier.weight(1f), color = Color(0xFF1873B9) )
        Text(car.carModel, modifier = Modifier.weight(1f), color = Color(0xFF1873B9) )
        Text(car.carBrand, modifier = Modifier.weight(1f), color = Color(0xFF1873B9) )
    }
}
