package example.project.model

import example.project.model.database.ScannedDevice

data class ScanState(
    val devices: List<ScannedDevice>,
    val selectedDevice: DeviceDetail?,
    val bleMessage: ConnectionState,
    val userMessage: String?
)

enum class ConnectionState {
    CONNECTED,
    CONNECTING,
    DISCONNECTING,
    DISCONNECTED;

    fun toTitle() = this.name.lowercase().replaceFirstChar { it.uppercase() }
}

