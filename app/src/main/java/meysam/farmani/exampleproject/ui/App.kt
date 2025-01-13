package meysam.farmani.exampleproject.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import example.project.designsystem.theme.designBackgroundWhiteColor
import meysam.farmani.exampleproject.navigation.NavHost

@ExperimentalComposeUiApi
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun App(
    appState: AppState,
    startDestination: String,
) {

    val context = LocalContext.current

    Scaffold(
        containerColor = designBackgroundWhiteColor,
        contentColor = designBackgroundWhiteColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {

        }
    ) { paddingValues ->
        NavHost(
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            onBackClickWithData = appState::onBackClickWithData,
            context = context,
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues),
            startDestination = startDestination,
        )
    }
}
