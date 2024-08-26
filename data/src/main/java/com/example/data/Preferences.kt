package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

internal val Context.authTokenStore: DataStore<Preferences>
    by preferencesDataStore("authTokenStore")

internal val Context.profileStore: DataStore<Preferences>
    by preferencesDataStore("profileStore")

internal object Preferences {
    val ACCESS = stringPreferencesKey("ACCESS")
    val REFRESH = stringPreferencesKey("REFRESH")

    val PROFILE_ME = stringPreferencesKey("PROFILE_ME")
}