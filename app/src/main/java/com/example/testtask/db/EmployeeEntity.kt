package com.example.testtask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey
    @ColumnInfo(name = "check_in_date") val checkInDate: String
)