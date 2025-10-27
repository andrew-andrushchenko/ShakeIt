package com.andrii_a.shakeit.domain.preferences

import kotlinx.coroutines.flow.Flow

interface LocalPreferencesRepository {

    val appTheme: Flow<AppTheme>

    val appLanguage: Flow<AppLanguage>

    suspend fun updateAppTheme(appTheme: AppTheme)

    suspend fun updateAppLanguage(appLanguage: AppLanguage)

}