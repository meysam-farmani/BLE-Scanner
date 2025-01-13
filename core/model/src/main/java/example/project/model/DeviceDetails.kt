package example.project.model

import example.project.model.database.ScannedDevice

data class DeviceDetail(
    val scannedDevice: ScannedDevice,
    val services: List<DeviceService>
)

data class DeviceService(
    val uuid: String,
    val name: String,
    val characteristics: List<DeviceCharacteristics>
)


