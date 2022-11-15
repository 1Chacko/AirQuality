package com.example.airquality.data.local

import com.example.airquality.entity.AQStation
import com.example.airquality.logic.repository.LocalStationsRepository

class InMemoryStationsRepository: LocalStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }

    override suspend fun save(stations: List<AQStation>) {

    }
}