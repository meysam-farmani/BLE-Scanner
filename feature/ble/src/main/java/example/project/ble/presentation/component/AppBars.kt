package example.project.ble.presentation.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import example.project.designsystem.theme.BLEScannerTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppBarWithBackButton(
    title: String,
    onBackClicked: () -> Unit
) {

    CenterAlignedTopAppBar(
        //modifier = Modifier.border(2.dp, Color.Blue),
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                BackIcon(contentDesc = "Go Back")
            }
        }
    )
}

@Preview
@Composable
fun PreviewAppBar() {
    BLEScannerTheme() {
        AppBarWithBackButton(title = "ELK-BLEDOM") {
            
        }
    }
}

