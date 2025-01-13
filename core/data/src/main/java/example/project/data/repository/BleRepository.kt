package example.project.data.repository

import android.bluetooth.BluetoothGatt
import android.os.ParcelUuid
import example.project.model.database.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface BleRepository {
    suspend fun getCompanyById(id: Int): Company?
    suspend fun getServiceById(uuid: String): Service?
    suspend fun getCharacteristicById(uuid: String): BleCharacteristic?
    suspend fun getMicrosoftDeviceById(id: Int): MicrosoftDevice?
    suspend fun insertDevice(device: ScannedDevice): Long
    suspend fun getDeviceByAddress(address: String): ScannedDevice?
    suspend fun deleteScans()
    fun getScannedDevices(): Flow<List<ScannedDevice>>
    suspend fun getMsDevice(byteArray: ByteArray): String?
    suspend fun getServices(serviceIdRecord: List<ParcelUuid>): List<String>?
    suspend fun getDescriptorById(uuid: String): Descriptor?
}