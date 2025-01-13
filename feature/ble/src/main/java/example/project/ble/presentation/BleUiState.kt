package example.project.ble.presentation

sealed class BleUiEvent {
    data class Error(val message: String) : BleUiEvent()
}