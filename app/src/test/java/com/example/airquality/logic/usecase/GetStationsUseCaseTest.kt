package com.example.airquality.logic.usecase

import com.example.airquality.entity.AQStation
import com.example.airquality.logic.repository.RemoteStationsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetStationsUseCaseTest {

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToRemote() = runBlocking {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        sut.execute()
        assertEquals(1, remote.getAllCallsCount)
    }

}

class MockRemoteStationsRepository : RemoteStationsRepository {

    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return emptyList()
    }

}