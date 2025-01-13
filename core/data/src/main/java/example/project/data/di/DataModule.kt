package example.project.data.di

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import example.project.data.model.BleGatt
import example.project.data.model.BleManager
import example.project.data.repository.BleRepository
import example.project.data.repository.BleRepositoryImpl
import example.project.data.repository.SplashRepositoryImpl
import example.project.data.usecase.ParseDescriptor
import example.project.data.usecase.ParseNotification
import example.project.data.usecase.ParseRead
import example.project.data.usecase.ParseScanResult
import example.project.data.usecase.ParseService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideSplashRepositoryImpl(
        @ApplicationContext context: Context,
    ) = SplashRepositoryImpl(context = context)

//    @Provides
//    @Singleton
//    fun provideBluetoothAdapter(bluetoothManager: BluetoothManager): BluetoothAdapter =
//        bluetoothManager.adapter

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideBluetoothAdapter(application: Application): BluetoothAdapter =
        (application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    fun provideParseService(bleRepository: BleRepository): ParseService =
        ParseService(bleRepository)

    @Provides
    fun provideParseRead(): ParseRead = ParseRead()

    @Provides
    fun provideParseNotification(): ParseNotification = ParseNotification()

    @Provides
    fun provideParseDescriptor(): ParseDescriptor = ParseDescriptor()

    @Provides
    fun provideParseScanResult(bleRepository: BleRepository): ParseScanResult =
        ParseScanResult(bleRepository)

    @Provides
    fun provideBleGatt(
        application: Application,
        scope: CoroutineScope,
        bluetoothAdapter: BluetoothAdapter,
        parseService: ParseService,
        parseRead: ParseRead,
        parseNotification: ParseNotification,
        parseDescriptor: ParseDescriptor
    ): BleGatt = BleGatt(
        app = application,
        scope = scope,
        btAdapter = bluetoothAdapter,
        parseService = parseService,
        parseRead = parseRead,
        parseNotification = parseNotification,
        parseDescriptor = parseDescriptor
    )

    @Provides
    fun provideBleManager(
        bleRepository: BleRepository,
        scope: CoroutineScope,
        parseScanResult: ParseScanResult,
        bluetoothAdapter: BluetoothAdapter
    ): BleManager = BleManager(
        bleRepository = bleRepository,
        scope = scope,
        parseScanResult = parseScanResult,
        btAdapter = bluetoothAdapter
    )
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher