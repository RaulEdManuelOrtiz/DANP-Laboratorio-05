package com.danp.danp_laboratorio05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danp.danp_laboratorio05.data.Car
import com.danp.danp_laboratorio05.screens.AddScreen
import com.danp.danp_laboratorio05.screens.DetailsScreen
import com.danp.danp_laboratorio05.screens.EditScreen
import com.danp.danp_laboratorio05.screens.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val carViewModel by viewModels<CarViewModel> {
            CarViewModelFactory((application as CarApplication).repository)
        }

        setContent {
            CarCRUD(
                carViewModel,
                onAddItem = { carViewModel.addItem(it) },
                onEditItem = { carViewModel.updateItem(it) },
                onDeleteItem = { carViewModel.deleteItem(it) })
        }
    }
}


@Composable
fun CarCRUD(
    carViewModel: CarViewModel,
    onAddItem: (Car) -> Unit,
    onEditItem: (Car) -> Unit,
    onDeleteItem: (Car) -> Unit
) {
    val navController = rememberNavController()
    var canPop by remember { mutableStateOf(false) }

    var appTitle by remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(false) }

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
            if (showFab) { // display FAB based on the even from screens
                FloatingActionButton(onClick = { navController.navigate("add") }, backgroundColor = Color(0xFF093980)) {
                    Icon(imageVector = Icons.Filled.Add,
                        contentDescription = "Agregar vehiculo",
                        tint = Color.White

                    )
                }
            }
        },
        content = {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = { showFab = it }
                    )
                }
                composable("details/{itemId}") { backStackEntry ->
                    DetailsScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = { showFab = it },
                        onCarDeleted = { onDeleteItem(it) }
                    )
                }
                composable("add") {
                    AddScreen(
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = { showFab = it },
                        onCarAdded = { onAddItem(it) }
                    )
                }
                composable("edit/{itemId}") { backStackEntry ->
                    EditScreen(
                        backStackEntry.arguments?.getString("itemId"),
                        navController,
                        carViewModel,
                        onSetAppTitle = { appTitle = it },
                        onShowFab = { showFab = it },
                        onCarEdited = { onEditItem(it) }
                    )
                }
            }
        }
    )
}