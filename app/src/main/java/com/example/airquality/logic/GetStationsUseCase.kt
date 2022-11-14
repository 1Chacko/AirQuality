package com.example.airquality.logic

import com.example.airquality.entity.AQStation
import javax.inject.Inject

class GetStationsUseCase @Inject constructor() {

    fun execute(): List<AQStation> {
        return listOf(AQStation("1", "Jakub", "Zgierz", "AAA", "AAA"))
    }
}