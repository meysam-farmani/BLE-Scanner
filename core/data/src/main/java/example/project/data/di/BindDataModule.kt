package example.project.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.project.data.repository.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class BindDataModule {

    @Binds
    @Singleton
    abstract fun bindSplashRepository(splashRepositoryImpl: SplashRepositoryImpl): SplashRepository

    @Binds
    @Singleton
    abstract fun bindBleRepository(bleRepositoryImpl: BleRepositoryImpl): BleRepository
}