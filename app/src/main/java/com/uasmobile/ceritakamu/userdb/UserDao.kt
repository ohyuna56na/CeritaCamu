package com.uasmobile.ceritakamu.userdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun isEmailRegistered(email: String): User?

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User>

    @Update
    suspend fun updateUser(user: User)
}

