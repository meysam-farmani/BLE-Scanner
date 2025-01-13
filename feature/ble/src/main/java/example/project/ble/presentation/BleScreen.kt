package example.project.ble.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import example.project.ble.presentation.component.*
import example.project.designsystem.theme.BLEScannerTheme
import util.permissionsList
import example.project.model.database.ScannedDevice

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BleScreen(
    vm: BleViewModel = hiltViewModel()
) {

    val scanState = vm.scanState.collectAsStateWithLifecycle().value
    val devices = scanState.devices
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissionsList)

    LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
        if (multiplePermissionsState.allPermissionsGranted) {
            vm.startScan()
        }
    }

    val appSnackBarHostState = remember { SnackbarHostState() }
    scanState.userMessage?.let { userMessage ->
        LaunchedEffect(scanState.userMessage, userMessage) {
            appSnackBarHostState.showSnackbar(userMessage)
            vm.userMessageShown()
        }
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        snackbarHost =  { SnackbarHost(hostState = appSnackBarHostState) },
    ) { padding ->

        if (!multiplePermissionsState.allPermissionsGranted) {
            ShowPermissions(multiplePermissionsState)
        } else {
            if (scanState.selectedDevice == null)
                ScannedDeviceList(devices, vm::onConnect, padding)
            else
                ShowDevice(
                    paddingValues = padding,
                    scanState = scanState,
                    onConnect = vm::onConnect,
                    onDisconnect = vm::onDisconnect,
                    onBack = vm::onBackFromDevice,
                    onRead = vm::readCharacteristic,
                    onShowUserMessage = vm::showUserMessage,
                    onWrite = vm::onWriteCharacteristic,
                    onReadDescriptor = vm::readDescriptor,
                    onWriteDescriptor = vm::writeDescriptor
                )
        }
    }

}

@Composable
private fun ScannedDeviceList(
    devices: List<ScannedDevice>,
    onClick: (String) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            //.padding(paddingValues)
            .padding(8.dp)
    ) {
        items(devices) { device ->

            ScannedDevice(device = device, onClick = onClick)
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ListPreview() {

    val deviceList = listOf(
        ScannedDevice(
            deviceId = 0,
            deviceName = "ELK-BLEDOM",
            address = "BE:00:FA:00:XX:00",
            rssi = -77,
            manufacturer = null,
            services = listOf("[Human Interface Device"),
            extra = null,
            lastSeen = 1674510398719
        ),
        ScannedDevice(
            deviceId = 0,
            deviceName = "EASYWAY-BLE",
            address = "93:00:44:00:XX:AC",
            rssi = -81,
            manufacturer = "Ericsson Technology Licensing",
            services = null,
            extra = null,
            lastSeen = 1674510397416
        )
    )

    BLEScannerTheme {
        Surface() {
            ScannedDeviceList(devices = deviceList, onClick = {}, PaddingValues(4.dp))
        }
    }

}