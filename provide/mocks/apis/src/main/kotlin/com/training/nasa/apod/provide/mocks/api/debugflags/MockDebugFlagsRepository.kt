package com.training.nasa.apod.provide.mocks.api.debugflags

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
class MockDebugFlagsRepository @Inject constructor(var mockDebugFlags: MockDebugFlags) :
    IMockDebugFlagsRepository {

    override fun saveMockDebugFlags(mockDebugFlags: MockDebugFlags) {
        this.mockDebugFlags = mockDebugFlags
    }

    override fun loadMockDebugFlags(): MockDebugFlags {
        return mockDebugFlags
    }

    override suspend fun handleError() {
        mockDebugFlags.run {
            delay(delayInMillis)
            apiErrorType.handleError()
        }
    }
}
