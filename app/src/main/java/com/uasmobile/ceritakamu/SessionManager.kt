package com.uasmobile.ceritakamu

import android.content.Context
import com.uasmobile.ceritakamu.userdb.User

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    fun isOnboardingShown(): Boolean {
        return sharedPreferences.getBoolean("onboarding_shown", false)
    }

    fun setOnboardingShown() {
        sharedPreferences.edit().putBoolean("onboarding_shown", true).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains("user_id")
    }

    fun saveUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", user.id)
        editor.putString("user_name", user.name)
        editor.putString("user_email", user.email)
        editor.apply()
    }

    fun getUser(): User? {
        val id = sharedPreferences.getInt("user_id", -1)
        val name = sharedPreferences.getString("user_name", null)
        val email = sharedPreferences.getString("user_email", null)
        return if (id != -1 && name != null && email != null) {
            User(id, name, email, "")
        } else {
            null
        }
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}