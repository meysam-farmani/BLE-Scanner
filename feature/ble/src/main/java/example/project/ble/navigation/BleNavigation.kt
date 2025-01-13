package example.project.ble.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import example.project.ble.presentation.BleScreen

const val bleRoute = "ble_route"
const val bleGraphRoutePattern = "ble_graph"

fun NavController.navigateToBle(navOptions: NavOptions? = null) {
    this.navigate(bleGraphRoutePattern, navOptions)
}

@SuppressLint("MissingPermission")
fun NavGraphBuilder.bleScreenGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = bleGraphRoutePattern,
        startDestination = bleRoute,
    ) {
        composable(route = bleRoute) {
            BleScreen()
        }
        nestedGraphs()
    }
}