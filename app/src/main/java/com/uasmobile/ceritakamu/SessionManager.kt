package com.uasmobile.ceritakamu

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "book_manager_prefs"
        private const val KEY_ONBOARDING_SHOWN = "onboarding_shown"
    }

    fun isOnboardingShown(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_SHOWN, false)
    }

    fun setOnboardingShown(isShown: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_SHOWN, isShown).apply()
    }
}