package com.example.testtask.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}