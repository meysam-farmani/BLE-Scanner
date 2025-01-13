package example.project.domain.use_case

import example.project.data.repository.BleRepository
import example.project.data.repository.SplashRepository
import example.project.model.database.ScannedDevice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScannedDevicesUseCase @Inject constructor(private val repository: BleRepository) {
    operator fun invoke(): Flow<List<ScannedDevice>> = repository.getScannedDevices()
}