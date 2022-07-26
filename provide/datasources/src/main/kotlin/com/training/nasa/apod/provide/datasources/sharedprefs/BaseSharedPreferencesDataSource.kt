package com.training.nasa.apod.provide.datasources.sharedprefs

import android.content.Context
import android.content.SharedPreferences

abstract class BaseSharedPreferencesDataSource(context: Context) {

    abstract val fileName: String

    protected val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            fileName,
            Context.MODE_PRIVATE
        )
    }
}
