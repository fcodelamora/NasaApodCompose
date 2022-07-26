package com.training.nasa.apod.core.usecases.test.settings

import com.training.nasa.apod.core.entities.UserPreferences
import com.training.nasa.apod.core.repository.IUserPreferencesRepository
import com.training.nasa.apod.core.usecases.feature.settings.IStoreUserPreferencesView
import com.training.nasa.apod.core.usecases.feature.settings.StoreUserPreferencesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.given

class StoreUserPreferencesUseCaseTest {

    private lateinit var mockRepository: IUserPreferencesRepository
    private lateinit var mockView: IStoreUserPreferencesView

    private lateinit var useCase: StoreUserPreferencesUseCase

    @BeforeEach
    fun before() {
        mockRepository = mock(IUserPreferencesRepository::class.java)
        mockView = mock(IStoreUserPreferencesView::class.java)

        useCase = StoreUserPreferencesUseCase(mockView, mockRepository)
    }

    @Test
    fun `execute - Retrieve user preference`() = runBlocking {
        val userPreferences = UserPreferences()

        useCase.execute(userPreferences)

        verify(mockRepository, times(1)).storeUserPreferences(userPreferences)
        verify(mockView, never()).handleException(anyOrNull())
    }

    @Test
    fun `execute - ERROR - Failed to Retrieve user preference`() = runBlocking {

        val userPreferences = UserPreferences()
        val exception = Exception()

        given(
            mockRepository.storeUserPreferences(userPreferences)
        ).willAnswer {
            throw exception
        }

        useCase.execute(userPreferences)

        verify(mockRepository, times(1)).storeUserPreferences(userPreferences)
        verify(mockView, times(1)).handleException(exception)
    }
}
