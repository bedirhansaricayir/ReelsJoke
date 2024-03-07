package com.reelsjoke.app.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject


/**
 * Created by bedirhansaricayir on 3.02.2024.
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferencesImpl @Inject constructor(
    @ApplicationContext val context: Context
) : AppPreferences {

    private object PreferencesKey {
        val showBalloon = booleanPreferencesKey(name = "show_balloon")
        val isPremium = booleanPreferencesKey(name = "is_premium")
    }

    override suspend fun setBalloonState(state: Boolean) {
        context.dataStore.edit { pref ->
            pref[PreferencesKey.showBalloon] = state
        }
    }

    override fun getBalloonState(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else
                    throw exception
            }.map { pref ->
                pref[PreferencesKey.showBalloon] ?: false
            }
    }

    override suspend fun setPremium(state: Boolean) {
        context.dataStore.edit { pref ->
            pref[PreferencesKey.isPremium] = state
        }
    }

    override fun getPremiumState(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else
                    throw exception
            }.map { pref ->
                pref[PreferencesKey.isPremium] ?: false
            }
    }


}


interface AppPreferences {

    suspend fun setBalloonState(state: Boolean)

    fun getBalloonState(): Flow<Boolean>

    suspend fun setPremium(state: Boolean)

    fun getPremiumState(): Flow<Boolean>
}