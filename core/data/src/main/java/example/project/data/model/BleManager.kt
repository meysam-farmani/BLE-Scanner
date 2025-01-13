package example.project.data.model

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.ParcelUuid
import android.util.SparseArray
import example.project.data.repository.BleRepository
import example.project.data.usecase.ParseScanResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BleManager @Inject constructor(
    private val bleRepository: BleRepository,
    private val scope: CoroutineScope,
    private val parseScanResult: ParseScanResult,
    private val btAdapter: BluetoothAdapter // Provided via Hilt
) {

    var isScanning = false

    private val btScanner = btAdapter.bluetoothLeScanner

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val scanCallback = object : ScanCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)

            scope.launch {
                parseScanResult(result)
            }

        }

    }

    init {
        scope.launch {
            bleRepository.deleteScans()
            //scan()
        }
    }

    @SuppressLint("MissingPermission")
    fun scan() {
        isScanning = true
        btScanner.startScan(null,scanSettings,scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        isScanning = false
        btScanner.stopScan(scanCallback)
    }

}