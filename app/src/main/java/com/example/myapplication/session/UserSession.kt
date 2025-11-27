package com.example.myapplication.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("user_session")

class UserSession(private val context: Context) {

    companion object {
        private val KEY_ID = intPreferencesKey("id_usuario")
        private val KEY_NOMBRE = stringPreferencesKey("nombre_usuario")
        private val KEY_CORREO = stringPreferencesKey("correo_usuario")
        private val KEY_LOGGED = booleanPreferencesKey("logged_in")
    }


    val idUsuario: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[KEY_ID] ?: 0
    }

    val nombreUsuario: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_NOMBRE] ?: ""
    }

    val correoUsuario: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_CORREO] ?: ""
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED] ?: false
    }


    suspend fun saveUser(id: Int, nombre: String, correo: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ID] = id
            prefs[KEY_NOMBRE] = nombre
            prefs[KEY_CORREO] = correo
            prefs[KEY_LOGGED] = true
        }
    }


    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
