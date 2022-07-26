package com.training.nasa.apod.provide.mocks.api.debugflags

interface IMockDebugFlagsRepository {
    fun saveMockDebugFlags(mockDebugFlags: MockDebugFlags)

    fun loadMockDebugFlags(): MockDebugFlags

    suspend fun handleError()
}
