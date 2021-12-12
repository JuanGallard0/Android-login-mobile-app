package com.pdm.ndv

import android.app.Application
import com.pdm.ndv.data.NDVDatabase
import com.pdm.ndv.network.NDVAPI
import com.pdm.ndv.repositories.UserRepository

class NDVApplication : Application() {
    private val database by lazy {
        NDVDatabase.getDatabase(this)
    }

    val userRepository by lazy {
        val nvdDao = database.ndvDao()
        UserRepository(NDVAPI, nvdDao)
    }
}
