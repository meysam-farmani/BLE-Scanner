package example.project.data.repository

import kotlinx.coroutines.flow.Flow

interface SplashRepository {
    fun getAppVersionName(): Flow<String>
}