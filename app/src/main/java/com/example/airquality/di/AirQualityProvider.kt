package com.example.airquality.di

import android.content.Context
import androidx.room.Room
import com.example.airquality.data.AirlyStationDataSource
import com.example.airquality.data.local.db.AppDatabase
import com.example.airquality.data.local.db.DatabaseStationsRepository
import com.example.airquality.logic.repository.LocalStationsRepository
import com.example.airquality.logic.repository.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {

    @Provides
    @Singleton
    fun provideLocalStationsRepository(@ApplicationContext context: Context): LocalStationsRepository {
        val dataBase = Room.databaseBuilder(context, AppDatabase::class.java, "AirQualityDb").build()
        return DatabaseStationsRepository(dataBase)
    }

    @Provides
    @Singleton
    fun provideAirlyService(retrofit: Retrofit): AirlyStationDataSource.AirlyService {
        return retrofit.create(AirlyStationDataSource.AirlyService::class.java)
    }

    @Provides
    @Singleton
    fun provideAirlyAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(AirlyAuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlyStationDataSource.HOST)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(airlyService: AirlyStationDataSource.AirlyService): RemoteStationsRepository {
        return AirlyStationDataSource(airlyService)
    }
}

class AirlyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("apikey", "iHPNP9d1Xd4e1Sv8wfbAc3lMO2kald3A")

        return chain.proceed(requestBuilder.build())
    }

}