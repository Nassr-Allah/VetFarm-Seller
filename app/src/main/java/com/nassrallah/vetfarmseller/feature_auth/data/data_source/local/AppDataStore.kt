package com.nassrallah.vetfarmseller.feature_auth.data.data_source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val TOKEN_KEY = stringPreferencesKey("token")
private val ID_KEY = intPreferencesKey("id")
private val CATEGORY_KEY = stringPreferencesKey("category")

class AppDataStore(
    private val dataStore: DataStore<Preferences>
) {

    val token: Flow<String> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY].orEmpty()
    }

    val id: Flow<Int> = dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: 0
    }

    val category: Flow<String> = dataStore.data.map { preferences ->
        preferences[CATEGORY_KEY].orEmpty()
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveId(id: Int) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }

    suspend fun saveCategory(category: String) {
        dataStore.edit { preferences ->
            preferences[CATEGORY_KEY] = category
        }
    }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = 0
            preferences[TOKEN_KEY] = ""
            preferences[CATEGORY_KEY] = ""
        }
    }


}