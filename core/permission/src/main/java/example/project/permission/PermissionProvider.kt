package example.project.permission

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionProvider @Inject constructor(@ApplicationContext val context: Context) {
    fun hasPermission(permissions: Array<String>) = context.hasPermission(*permissions)

    fun hasUsageStatsPermission() = context.hasUsageStatsPermission()
    fun hasAlarmPermission() = context.hasAlarmPermission()

    fun isIgnoringBatteryOptimizations() = context.isIgnoringBatteryOptimizations()
}