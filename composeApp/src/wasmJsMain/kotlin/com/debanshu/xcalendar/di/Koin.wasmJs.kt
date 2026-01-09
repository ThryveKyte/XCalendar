package com.debanshu.xcalendar.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.debanshu.xcalendar.data.localDataSource.AppDatabase

actual fun getDatabase(): AppDatabase {
    return Room.databaseBuilder<AppDatabase>(
        name = "calendar.db",
        factory = { AppDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}

// This is required for Room on Wasm
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect fun AppDatabase.Companion.instantiateImpl(): AppDatabase
