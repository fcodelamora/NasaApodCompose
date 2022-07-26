package com.training.nasa.apod.core.usecases.test.settings

import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.core.usecases.feature.settings.GetUserPreferencesUseCase
import com.training.nasa.apod.core.usecases.feature.settings.IGetUserPreferencesView
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.given

class GetUserPreferencesUseCaseTest {

    private lateinit var mockRepository: IUserPreferencesRepository

    private lateinit var mockView: IGetUserPreferencesView

    private lateinit var useCase: GetUserPreferencesUseCase

    @BeforeEach
    fun before() {
        mockRepository = mock(IUserPreferencesRepository::class.java)
        mockView = mock(IGetUserPreferencesView::class.java)

        useCase = GetUserPreferencesUseCase(mockView, mockRepository)
    }

    @Test
    fun `execute - Retrieve user preference`() = runBlocking {
        useCase.execute()

        verify(mockView, times(1)).onUserPreferencesRetrieved(anyOrNull())
        verify(mockView, never()).showErrorView(anyOrNull())
        verify(mockView, never()).handleException(anyOrNull())
    }

    @Test
    fun `execute - ERROR - Failed to Retrieve user preference`() = runBlocking {

        val exception = Exception()

        given(
            mockRepository.getUserPreferences()
        ).willAnswer {
            throw exception
        }

        useCase.execute()

        verify(mockView, times(1)).handleException(exception)
        verify(mockView, never()).onUserPreferencesRetrieved(anyOrNull())
        verify(mockView, never()).showErrorView(anyOrNull())
    }
}
