package com.pdm.ndv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pdm.ndv.data.dao.NDVDao
import com.pdm.ndv.data.entities.UserAuth

@Database(version = 1, entities = [UserAuth::class])
abstract class NDVDatabase: RoomDatabase() {

    abstract fun ndvDao(): NDVDao

    companion object {
        @Volatile
        private var INSTANCE: NDVDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                NDVDatabase::class.java,
                "ndv_db"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}
