package meysam.farmani.exampleproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import example.project.designsystem.theme.BLEScannerTheme
import example.project.designsystem.theme.designBackgroundWhiteColor

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        const val Initial_Screen = "initialScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val startDestination = intent.getStringExtra(Initial_Screen)

        setContent {
            val systemUiController = rememberSystemUiController()

            BLEScannerTheme {
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = designBackgroundWhiteColor
                ) {

                    Column(modifier = Modifier.fillMaxSize()) {
                        val windowsClass = calculateWindowSizeClass(activity = this@MainActivity)
                        App(
                            startDestination = startDestination ?: "",
                            appState = rememberExampleProjectAppState(
                                activity = this@MainActivity,
                                windowSizeClass = windowsClass,
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}