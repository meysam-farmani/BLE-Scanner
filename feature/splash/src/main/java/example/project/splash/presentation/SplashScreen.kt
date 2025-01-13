package example.project.splash.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import example.project.designsystem.theme.*
import meysam.farmani.exampleproject.core.designsystem.R

@Composable
fun SplashScreen(
        modifier: Modifier = Modifier,
        viewModel: SplashViewModel = hiltViewModel(),
        onNavigateToMain: () -> Unit,
    ) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {

        viewModel.event.collect { event ->
            when (event) {
                is SplashUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is SplashUiEvent.NavigateToMain -> {
                    onNavigateToMain()
                }
            }
        }
    }

    SplashScreenBody(
        modifier = modifier,
        appVersionName = uiState.appVersionName!!
    )
}

@Composable
fun SplashScreenBody(
    modifier: Modifier,
    appVersionName: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 54.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )

            val backgroundImage = if (isSystemInDarkTheme())
                painterResource(id = R.drawable.logo_light)
            else
                painterResource(id = R.drawable.logo_dark)

            Image(
                painter = backgroundImage,
                contentDescription = "",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.FillBounds,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            AppVersionText(
                appVersionName = appVersionName,
                modifier = modifier,
            )

            Spacer(modifier = Modifier.height(4.dp))

            PoweredByText(
                modifier = modifier,
                text = stringResource(R.string.splash_text),
            )
        }
    }
}

@Composable
fun AppVersionText(
    modifier: Modifier,
    appVersionName: String
) {
    Text(
        modifier = modifier,
        text = "EXAMPLE PROJECT $appVersionName",
        style = typography.labelSmall.copy(
            fontFamily = monoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun PoweredByText(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = typography.labelSmall.copy(
            fontFamily = monoFamily,
            fontWeight = FontWeight.Light,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Preview
@Composable
fun SplashPreview() {
    BLEScannerTheme {
        SplashScreenBody(
            modifier = Modifier,
            appVersionName = "0.0.0"
        )
    }
}