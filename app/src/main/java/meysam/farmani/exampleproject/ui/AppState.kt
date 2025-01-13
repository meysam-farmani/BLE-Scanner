package meysam.farmani.exampleproject.ui

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberExampleProjectAppState(
    activity: Activity,
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(navController, coroutineScope, windowSizeClass) {
        AppState(activity, navController, coroutineScope, windowSizeClass)
    }
}

@Stable
class AppState(
    private val activity: Activity,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {

    fun onBackClick() {
        when (navController.currentDestination?.route) {

            else -> {
                navController.popBackStack()
            }
        }
    }

    fun onBackClickWithData(key: String, data: String) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(key, data)
        navController.popBackStack()
    }
}