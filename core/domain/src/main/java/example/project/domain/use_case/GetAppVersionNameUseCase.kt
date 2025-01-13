package example.project.domain.use_case

import example.project.data.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppVersionNameUseCase @Inject constructor(private val repository: SplashRepository) {
    operator fun invoke(): Flow<String> = repository.getAppVersionName()
}