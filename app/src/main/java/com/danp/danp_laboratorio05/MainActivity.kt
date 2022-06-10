package com.danp.danp_laboratorio05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danp.danp_laboratorio05.data.Car
import com.danp.danp_laboratorio05.data.Person
import com.danp.danp_laboratorio05.screens.AddScreen
import com.danp.danp_laboratorio05.screens.DetailsScreen
import com.danp.danp_laboratorio05.screens.EditScreen
import com.danp.danp_laboratorio05.screens.HomeScreen
import com.danp.danp_laboratorio05.screens.person.HomeScreen as PersonHomeScreen
import com.danp.danp_laboratorio05.screens.person.AddScreen as PersonAddScreen
import com.danp.danp_laboratorio05.screens.person.DetailsScreen as PersonDetailsScreen
import com.danp.danp_laboratorio05.screens.person.EditScreen as PersonEditScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val carViewModel by viewModels<CarViewModel> {
            CarViewModelFactory((application as CarApplication).repository)
        }
        val personViewModel by viewModels<PersonViewModel> {
            PersonViewModelFactory((application as CarApplication).personRepository)
        }

        setContent {
            CarCRUD(
                carViewModel,
                personViewModel,
                onAddItem = { carViewModel.addItem(it) },
                onEditItem = { carViewModel.updateItem(it) },
                onDeleteItem = { carViewModel.deleteItem(it) },
                onAddPerson = { personViewModel.addItem(it) },
                onEditPerson = { personViewModel.updateItem(it) },
                onDeletePerson = { personViewModel.deleteItem(it) })
        }
    }
}



@Composable
fun CarCRUD(
    carViewModel: CarViewModel,
    personViewModel: PersonViewModel,
    onAddItem: (Car) -> Unit,
    onEditItem: (Car) -> Unit,
    onDeleteItem: (Car) -> Unit,
    onAddPerson: (Person) -> Unit,
    onEditPerson: (Person) -> Unit,
    onDeletePerson: (Person) -> Unit
) {
    val navController = rememberNavController()
    var canPop by remember { mutableStateOf(false) }

    var appTitle by remember { mutableStateOf("") }
    var showCarFab by remember { mutableStateOf(false) }
    var showPersonFab by remember { mutableStateOf(false) }

    fun showFab(carFab: Boolean, personFab: Boolean){
        showCarFab = carFab
        showPersonFab = personFab
    }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        canPop = controller.previousBackStackEntry != null
    }

    val navigationIcon: (@Composable () -> Unit)? =
        if (canPop) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        } else {
            null
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appTitle, color = Color.White, textAlign = TextAlign.Center
                ) },
                navigationIcon = navigationIcon,
                backgroundColor = Color(0xFF093980),
            ) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {

            if (showCarFab) { // display FAB based on the even from screens
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End,
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate("add") },
                        backgroundColor = Color(0xFF093980)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Agregar vehiculo",
                            tint = Color.White

                        )
                    }
                    FloatingActionButton(
                        onClick = { navController.navigate("personHome") },
                        backgroundColor = Color(0xFF093980)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Personas",
                            tint = Color.White

                        )
                    }
                }
            } else if (showPersonFab) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End,
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate("personAdd") },
                        backgroundColor = Color(0xFF093980)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Agregar persona",
                            tint = Color.White

                        )
                    }
                    FloatingActionButton(
                        onClick = { navController.navigate("home") },
                        backgroundColor = Color(0xFF093980)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Vehiculos",
                            tint = Color.White

                        )
                    }
            }
        }},
        content = {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab
                    )
                }
                composable("details/{itemId}") { backStackEntry ->
                    DetailsScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onCarDeleted = { onDeleteItem(it) }
                    )
                }
                composable("add") {
                    AddScreen(
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onCarAdded = { onAddItem(it) }
                    )
                }
                composable("edit/{itemId}") { backStackEntry ->
                    EditScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onCarEdited = { onEditItem(it) }
                    )
                }
                composable("personHome") {
                    PersonHomeScreen(
                        navController,
                        personViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab
                    )
                }
                composable("personDetails/{itemId}") { backStackEntry ->
                    PersonDetailsScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        personViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onPersonDeleted = { onDeletePerson(it) }
                    )
                }
                composable("personAdd") {
                    PersonAddScreen(
                        navController,
                        personViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onPersonAdded = { onAddPerson(it) }
                    )
                }
                composable("personEdit/{itemId}") { backStackEntry ->
                    PersonEditScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        personViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = ::showFab,
                        onPersonEdited = { onEditPerson(it) }
                    )
                }
            }
        }
    )
}