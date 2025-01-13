package meysam.farmani.exampleproject.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import example.project.ble.navigation.bleScreenGraph
import kotlin.reflect.KFunction2

@ExperimentalComposeUiApi
@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier,
    onBackClick: () -> Unit,
    onBackClickWithData: KFunction2<String, String, Unit>,
    context: Context,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        bleScreenGraph(
            nestedGraphs = {
            }
        )
    }
}