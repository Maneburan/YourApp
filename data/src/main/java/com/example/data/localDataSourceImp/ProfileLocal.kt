package com.example.data.localDataSourceImp

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.Preferences
import com.example.data.core.Profile
import com.example.data.profileStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProfileLocal @Inject constructor(
    private val context: Context
): Profile.LocalSource {

    override fun getMe(): Flow<String?> = context.profileStore.data.map { pref ->
        pref[Preferences.PROFILE_ME]
    }

    override suspend fun saveMe(json: String) {
        context.profileStore.edit { pref->
            pref[Preferences.PROFILE_ME] = json
        }
    }

    override suspend fun removeMe() {
        context.profileStore.edit { pref->
            pref.remove(Preferences.PROFILE_ME)
        }
    }

}