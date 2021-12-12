package com.pdm.ndv.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdm.ndv.data.entities.UserAuth

@Dao
interface NDVDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAuthUser(userAuth: UserAuth)

    @Query("DELETE from userAuth_table WHERE id = :id")
    suspend fun deleteUserAuthById(id: Int)

    @Query("SELECT * from userAuth_table WHERE id = :id")
    suspend fun getUserAuthById(id: Int): UserAuth
}