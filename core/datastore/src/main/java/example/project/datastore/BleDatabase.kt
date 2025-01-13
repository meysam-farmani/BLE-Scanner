package example.project.datastore

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import example.project.model.database.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(
    entities = [Company::class, Service::class, BleCharacteristic::class,
        MicrosoftDevice::class, ScannedDevice::class, Descriptor::class],
    version = 9, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BleDatabase : RoomDatabase() {
    abstract fun bleDao(): BleDao
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) =
        if (value.startsWith("["))
            Json.decodeFromString<List<String>>(value)
        else null

}