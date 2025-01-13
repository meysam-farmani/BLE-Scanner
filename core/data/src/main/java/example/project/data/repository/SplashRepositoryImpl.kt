package example.project.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val context: Context,
) :
    SplashRepository {
    override fun getAppVersionName(): Flow<String> = flow{
        val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        emit(versionName)
    }
}