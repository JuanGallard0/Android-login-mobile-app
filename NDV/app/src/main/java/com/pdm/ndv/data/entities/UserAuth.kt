package com.pdm.ndv.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userAuth_table")
data class UserAuth(
    @PrimaryKey var id: Int,
    var token: String,
    var username: String,
)
