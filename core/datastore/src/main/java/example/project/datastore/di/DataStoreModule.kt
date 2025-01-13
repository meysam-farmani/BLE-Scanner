package example.project.datastore.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import example.project.datastore.BleDatabase
import util.Constants.EXAMPLE_PROJECT_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext context: Context): BleDatabase =
        Room.databaseBuilder(
            context,
            BleDatabase::class.java,
            EXAMPLE_PROJECT_DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .createFromAsset("database/ble.db")
        .build()

    @Singleton
    @Provides
    fun provideCartDao(database: BleDatabase) = database.bleDao()
}
