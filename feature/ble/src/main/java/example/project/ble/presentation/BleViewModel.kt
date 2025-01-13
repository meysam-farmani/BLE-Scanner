package example.project.ble.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.project.data.di.IoDispatcher
import example.project.data.model.BleGatt
import example.project.data.model.BleManager
import example.project.data.repository.BleRepository
import example.project.domain.use_case.GetScannedDevicesUseCase
import util.decodeHex
import example.project.model.ConnectionState
import example.project.model.DeviceDetail
import example.project.model.ScanState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BleViewModel @Inject constructor(
    private val bleManager: BleManager,
    private val bleGatt: BleGatt,
    private val getScannedDevicesUseCase: GetScannedDevicesUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _devices = getScannedDevicesUseCase()
    private val _selectedDevice = MutableStateFlow<DeviceDetail?>(null)
    private val _bleMessage = bleGatt.connectMessage
    private val _userMessage = MutableStateFlow<String?>(null)

    private val _deviceDetails = bleGatt.deviceDetails

    val scanState = combine(
        _devices, _selectedDevice,
        _bleMessage, _userMessage, _deviceDetails
    ) { devices, selectedDevice, bleMessage, userMessage, deviceDetails ->

        Timber.d(deviceDetails.toString())

        val currentDevice = selectedDevice?.let {
            DeviceDetail(
                it.scannedDevice,
                deviceDetails
            )
        }

        ScanState(
            devices,
            currentDevice,
            bleMessage,
            userMessage
        )
    }.flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ScanState(emptyList(), null, ConnectionState.DISCONNECTED, null)
        )

    fun startScan() {
        bleManager.scan()
    }

    fun stopScan() {
        bleManager.stopScan()
    }

    fun onConnect(address: String) {
        Timber.d("calling connect...")
        val scannedDevice = scanState.value.devices.find {
            it.address == address
        }

        scannedDevice?.let {
            _selectedDevice.value = DeviceDetail(
                scannedDevice,
                emptyList()
            )
            stopScan()
            bleGatt.connect(address)
        }
    }

    fun readCharacteristic(uuid: String) {
        bleGatt.readCharacteristic(uuid)
    }

    fun readDescriptor(charUuid: String, descUuid: String) {
        bleGatt.readDescriptor(charUuid, descUuid)
    }

    fun writeDescriptor(charUuid: String, descUuid: String, hexString: String) {
        try {
            if (hexString.isNotEmpty()) {
                bleGatt.writeDescriptor(charUuid, descUuid, hexString.decodeHex())
            } else
                showUserMessage("Hex can't be null.")
        } catch (badHex: Exception) {
            showUserMessage("Invalid Hex String. Must be an even count.")
        }
    }

    fun onBackFromDevice() {
        bleGatt.close()
        _selectedDevice.value = null
        startScan()
    }

    fun onDisconnect() {
        Timber.d("calling disconnect...")
        bleGatt.close()
    }

    fun onWriteCharacteristic(uuid: String, bytes: String) {
        try {
            if (bytes.isNotEmpty()) {
                bleGatt.writeBytes(uuid, bytes.decodeHex())
            } else
                showUserMessage("Hex can't be null.")
        } catch (badHex: Exception) {
            showUserMessage("Invalid Hex String. Must be an even count.")
        }
    }

    fun showUserMessage(message: String) {
        Timber.d("show message...")
        _userMessage.value = message
    }

    fun userMessageShown() {
        Timber.tag("debug").d("user message set to null.")
        _userMessage.value = null
    }
}