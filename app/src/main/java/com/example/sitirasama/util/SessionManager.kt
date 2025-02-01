package com.example.sitirasama.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "SitiraSamaSession"
        private const val TOKEN_KEY = "TOKEN"
        private const val USERNAME_KEY = "USERNAME"
        private const val STATUS_KEY = "STATUS"
    }

    fun saveSession(token: String, username: String, status: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.putString(USERNAME_KEY, username)
        editor.putString(STATUS_KEY, status)
        editor.apply()

        Log.d("SESSION_DEBUG", "Token disimpan: $token")  // Debugging token yang disimpan
    }

    fun getToken(): String? {
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        Log.d("SESSION_DEBUG", "Token diambil: $token")  // Debugging token yang diambil
        return token
    }

    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        Log.d("SESSION_DEBUG", "Session dihapus!")  // Debugging saat sesi dihapus
    }
}
