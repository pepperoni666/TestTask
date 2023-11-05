package com.example.testtask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee ORDER BY datetime(check_in_date) DESC LIMIT 1")
    fun getEmployeeFlow(): Flow<EmployeeEntity?>
    @Insert
    suspend fun insertEmployee(employeeEntity: EmployeeEntity)
}