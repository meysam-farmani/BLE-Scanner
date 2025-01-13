package example.project.data.repository

import android.os.ParcelUuid
import example.project.datastore.BleDao
import example.project.model.database.*
import timber.log.Timber
import util.toGss
import util.toHex
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val dao: BleDao
) : BleRepository {

    override suspend fun getCompanyById(id: Int): Company? = dao.getCompanyById(id)

    override suspend fun getServiceById(uuid: String): Service? = dao.getServiceByUuid(uuid)

    override suspend fun getCharacteristicById(uuid: String): BleCharacteristic? {
        Timber.d(uuid)
        return dao.getCharacteristicsByUuid(uuid)
    }

    override suspend fun getMicrosoftDeviceById(id: Int): MicrosoftDevice? = dao.getMicrosoftDevice(id)

    override suspend fun insertDevice(device: ScannedDevice): Long {
        val existingDevice = dao.getDeviceByAddress(device.address)

        val deviceToUpsert = ScannedDevice(
            deviceId = existingDevice?.deviceId,
            deviceName = existingDevice?.deviceName ?: device.deviceName,
            address = device.address,
            rssi = device.rssi,
            manufacturer = existingDevice?.manufacturer ?: device.manufacturer,
            services = device.services,
            extra = device.extra,
            lastSeen = device.lastSeen
        )

        return dao.insertDevice(deviceToUpsert)
    }

    override suspend fun getDeviceByAddress(address: String) = dao.getDeviceByAddress(address)

    override suspend fun deleteScans() = dao.deleteScans()

    override fun getScannedDevices() = dao.getScannedDevices()

    override suspend fun getMsDevice(byteArray: ByteArray): String? {
        val msDeviceType = byteArray[1].toHex().toInt()
        return getMicrosoftDeviceById(msDeviceType)?.name
    }

    override suspend fun getServices(serviceIdRecord: List<ParcelUuid>): List<String>? {
        var serviceNames: MutableList<String>? = null

        serviceIdRecord.forEach { serviceId ->
            val formattedId = serviceId.uuid.toGss()
            getServiceById(formattedId)?.name?.let { serviceName ->
                if (serviceNames == null)
                    serviceNames = mutableListOf()
                serviceNames?.add(serviceName)
            }
        }

        return serviceNames?.toList()
    }

    override suspend fun getDescriptorById(uuid: String): Descriptor? = dao.getDescriptorByUuid(uuid)
}
