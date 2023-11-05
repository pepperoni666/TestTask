package com.example.testtask.remote

import com.example.testtask.utils.DateTimeFormatter
import java.util.Calendar

class RemoteService {
    suspend fun getDate(): DateResponse {
        val now = Calendar.getInstance()
        now.set(Calendar.MINUTE, 30)
        now.set(Calendar.HOUR_OF_DAY, 6)
        return DateResponse(DateTimeFormatter.formatFrom(now.time))
    }
}