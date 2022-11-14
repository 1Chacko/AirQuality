package com.example.airquality.di

import com.example.airquality.data.AirlyStationDataSource
import com.example.airquality.logic.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {

    @Provides
    fun provideAirlyService(): AirlyStationDataSource.AirlyService {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlyStationDataSource.HOST)
            .build()
            .create(AirlyStationDataSource.AirlyService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(airlyService: AirlyStationDataSource.AirlyService) : RemoteStationsRepository {
        return AirlyStationDataSource(airlyService)
    }
}