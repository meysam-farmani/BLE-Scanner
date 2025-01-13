package example.project.data.usecase

import android.bluetooth.BluetoothGattCharacteristic
import example.project.model.DeviceService
import example.project.model.updateNotification
import timber.log.Timber

class ParseNotification() {

    operator fun invoke(
        deviceDetails: List<DeviceService>,
        characteristic: BluetoothGattCharacteristic
    ): List<DeviceService> {

        val newList = deviceDetails.map { svc ->
            svc.copy(characteristics =
            svc.characteristics.map { char ->
                if (char.uuid == characteristic.uuid.toString()) {
                    char.updateNotification(characteristic.value)
                } else
                    char
            })
        }

        Timber.d("newList: $newList")

        return newList
    }

}