@file:OptIn(ExperimentalPermissionsApi::class)

package example.project.permission

import androidx.compose.runtime.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsHandler(permissionState: MultiplePermissionsState) {
    SideEffect {
        permissionState.launchMultiplePermissionRequest()
    }
}