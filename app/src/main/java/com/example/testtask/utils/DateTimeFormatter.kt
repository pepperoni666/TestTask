package com.example.testtask.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object DateTimeFormatter {

    private const val datePattern = "yyyy-MM-dd"
    private const val timePattern = "HH:mm"
    private const val pattern = "$datePattern $timePattern"

    fun formatFrom(time: Date): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(time)
    }

    fun getDateFromStr(dateTimeStr: String): String {
        val dateTimeFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val dateFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        val date = dateTimeFormat.parse(dateTimeStr)!!
        return dateFormat.format(date)
    }

    fun getTimeFromStr(dateTimeStr: String): String {
        val dateTimeFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val timeFormat = SimpleDateFormat(timePattern, Locale.getDefault())
        val date = dateTimeFormat.parse(dateTimeStr)!!
        return timeFormat.format(date)
    }

    fun calendarFromDateStr(dateStr: String): Calendar {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(datePattern, Locale.getDefault())
        calendar.time = sdf.parse(dateStr)!!
        return calendar
    }

    fun calendarFromTimeStr(timeStr: String): Calendar {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(timePattern, Locale.getDefault())
        calendar.time = sdf.parse(timeStr)!!
        return calendar
    }

    fun toDateString(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val sdf = SimpleDateFormat(datePattern, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun toTimeString(hourOfDay: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val sdf = SimpleDateFormat(timePattern, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun isDateTimeBeforeCurrent(dateTimeStr: String): Boolean {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.parse(dateTimeStr)!!.before(Date())
    }

    fun isValidDateTime(dateTimeStr: String): Boolean {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.isLenient = false
        return try {
            sdf.parse(dateTimeStr)
            true
        } catch (e: ParseException) {
            false
        }
    }
}