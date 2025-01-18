package com.uasmobile.ceritakamu

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uasmobile.ceritakamu.model.BookItem
import com.uasmobile.ceritakamu.userdb.User

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

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
        editor.putString("user_profile_image", user.profileImage) // Menyimpan URL gambar profil
        editor.apply()
    }

    fun getUser(): User? {
        val id = sharedPreferences.getInt("user_id", -1)
        val name = sharedPreferences.getString("user_name", null)
        val email = sharedPreferences.getString("user_email", null)
        val profileImage = sharedPreferences.getString("user_profile_image", null)
        return if (id != -1 && name != null && email != null) {
            User(id, name, email, "", profileImage)
        } else {
            null
        }
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveFavoriteBooks(userId: Int, books: List<BookItem>) {
        val json = gson.toJson(books)
        sharedPreferences.edit().putString("favorite_books_$userId", json).apply()
    }

    fun getFavoriteBooks(userId: Int): List<BookItem> {
        val json = sharedPreferences.getString("favorite_books_$userId", null)
        return if (json != null) {
            val type = object : TypeToken<List<BookItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}