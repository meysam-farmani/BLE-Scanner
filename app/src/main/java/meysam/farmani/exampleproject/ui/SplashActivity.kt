package meysam.farmani.exampleproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import example.project.ble.navigation.bleGraphRoutePattern
import example.project.designsystem.theme.BLEScannerTheme
import example.project.splash.presentation.SplashScreen
import example.project.splash.presentation.SplashViewModel

@SuppressLint("CustomSplashScreen")
@ExperimentalComposeUiApi
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    color = Color.Transparent
                ) {
                    SplashScreen( viewModel=viewModel){
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra(MainActivity.Initial_Screen, bleGraphRoutePattern)
                        startActivity(intent)
                        finish()
                        overridePendingTransition(0, 0)
                    }
                }
            }
        }
    }
}