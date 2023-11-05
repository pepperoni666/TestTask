package com.example.testtask.model

import com.example.testtask.db.EmployeeDao
import com.example.testtask.db.EmployeeEntity
import com.example.testtask.remote.RemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DateRepository(private val employeeDao: EmployeeDao, private val remoteService: RemoteService) {
    fun getDate(): Flow<String?> {
        return employeeDao.getEmployeeFlow()
            .flowOn(Dispatchers.IO)
            .map { it?.checkInDate }
    }

    suspend fun fetchDate() = withContext(Dispatchers.IO) {
        val response = remoteService.getDate()
        //should we check if response.dateTime is valid before inserting to database?
        setDate(response.dateTime)
    }

    suspend fun setDate(date: String) = withContext(Dispatchers.IO){
        employeeDao.insertEmployee(EmployeeEntity(date))
    }
}