package com.example.data.localDataSourceImp

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.core.AuthToken
import com.example.data.Preferences
import com.example.data.authTokenStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.data.core.AuthToken.LocalSource.Model
import javax.inject.Inject

internal class AuthTokenLocal @Inject constructor(
    private val context: Context
): AuthToken.LocalSource {

    override fun get(): Flow<Model.AuthToken?> = context.authTokenStore.data.map { pref ->
            val access = pref[Preferences.ACCESS]
            val refresh = pref[Preferences.REFRESH]

            if (access != null && refresh != null) {
                Model.AuthToken(
                    access = access,
                    refresh = refresh,
                )
            } else null
        }

    override suspend fun remove() {
        context.authTokenStore.edit { pref ->
            pref.remove(Preferences.ACCESS)
            pref.remove(Preferences.REFRESH)
        }
    }

    override suspend fun save(authToken: Model.AuthToken) {
        context.authTokenStore.edit { pref ->
            pref[Preferences.ACCESS] = authToken.access
            pref[Preferences.REFRESH] = authToken.refresh
        }
    }

}