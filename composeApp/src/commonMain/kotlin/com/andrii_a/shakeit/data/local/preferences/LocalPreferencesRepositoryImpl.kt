package com.andrii_a.shakeit.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andrii_a.shakeit.domain.preferences.AppLanguage
import com.andrii_a.shakeit.domain.preferences.AppTheme
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class LocalPreferencesRepositoryImpl(private val datastore: DataStore<Preferences>) : LocalPreferencesRepository {

    private val preferencesFlow = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    override val appTheme: Flow<AppTheme>
        get() = preferencesFlow.map { preferences ->
            AppTheme.valueOf(
                preferences[ShakeItAppPreferencesKeys.APP_THEME] ?: AppTheme.SYSTEM_DEFAULT.name
            )
        }

    override val appLanguage: Flow<AppLanguage>
        get() = preferencesFlow.map { preferences ->
            AppLanguage.valueOf(
                preferences[ShakeItAppPreferencesKeys.APP_LANGUAGE] ?: AppLanguage.ENGLISH.name
            )
        }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        datastore.edit { preferences ->
            preferences[ShakeItAppPreferencesKeys.APP_THEME] = appTheme.name
        }
    }

    override suspend fun updateAppLanguage(appLanguage: AppLanguage) {
        datastore.edit { preferences ->
            preferences[ShakeItAppPreferencesKeys.APP_LANGUAGE] = appLanguage.name
        }
    }

    object ShakeItAppPreferencesKeys {
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
    }
}