package com.training.nasa.apod.provide.datasources.sharedprefs

import android.content.Context
import androidx.core.content.edit
import com.training.nasa.apod.core.datasources.IUserPreferencesDataSource
import com.training.nasa.apod.core.entities.AppTheme
import com.training.nasa.apod.core.entities.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class SharedPreferencesUserPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) : BaseSharedPreferencesDataSource(context),
    IUserPreferencesDataSource {

    companion object {
        const val KEY_SELECTED_THEME: String = "key_selected_theme"
        const val KEY_HIDE_BACK_BUTTON_ON_IMAGES_FLAG: String =
            "key_hide_back_button_on_images_flag"
    }

    override val fileName: String = "user_preferences"

    override suspend fun saveUserPreferences(preferences: UserPreferences) =
        withContext(Dispatchers.IO) {
            sharedPrefs.edit(commit = true) {
                putInt(KEY_SELECTED_THEME, preferences.selectedTheme.id)
                putBoolean(KEY_HIDE_BACK_BUTTON_ON_IMAGES_FLAG, preferences.showBackButtonOnImages)
            }
        }

    override suspend fun getUserPreferences(): UserPreferences {

        val themeId = sharedPrefs.getInt(KEY_SELECTED_THEME, AppTheme.DYNAMIC.id)

        return UserPreferences(
            selectedTheme = AppTheme.getFromId(themeId),
            showBackButtonOnImages = sharedPrefs.getBoolean(
                KEY_HIDE_BACK_BUTTON_ON_IMAGES_FLAG,
                true
            )
        )
    }
}
