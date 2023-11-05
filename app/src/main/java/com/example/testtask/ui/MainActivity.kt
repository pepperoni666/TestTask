package com.example.testtask.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.R
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.utils.DateTimeFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity(), OnDateSetListener, OnTimeSetListener {

    private val mainViewModel: MainViewModel by viewModel()

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainViewModel.dateTime.observe(this) { dateTime ->
            dateTime?.let {
                binding?.dateET?.setText(DateTimeFormatter.getDateFromStr(it))
                binding?.timeET?.setText(DateTimeFormatter.getTimeFromStr(it))
                binding?.submitBtn?.isEnabled = false
            }
        }

        mainViewModel.loading.observe(this) {
            //TODO handle loading UI
        }

        mainViewModel.error.observe(this) {
            val text = when(it) {
                is MainViewModel.InvalidFormatException -> getString(R.string.invalid_format_error)
                is MainViewModel.FetchDataException -> getString(R.string.fetch_data_error)
                else -> getString(R.string.generic_error)
            }

            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }

        binding?.submitBtn?.setOnClickListener {
            mainViewModel.updateTime("${binding?.dateET?.text} ${binding?.timeET?.text}")
        }

        binding?.dateET?.setOnClickListener {
            binding?.dateET?.text?.let { dateStr ->
                val calendar = DateTimeFormatter.calendarFromDateStr(dateStr.toString())
                val dialog = DatePickerDialog(
                    this,
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.maxDate = Date().time
                dialog.show()
            }
        }

        binding?.timeET?.setOnClickListener {
            binding?.timeET?.text?.let { timeStr ->
                val calendar = DateTimeFormatter.calendarFromTimeStr(timeStr.toString())
                val dialog = TimePickerDialog(
                    this,
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                dialog.show()
            }
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val str = DateTimeFormatter.toDateString(year, month, dayOfMonth)
        if(DateTimeFormatter.isDateTimeBeforeCurrent("$str ${binding?.timeET?.text}")) {
            binding?.dateET?.setText(str)
            invalidateSubmit()
        }
        else {
            Toast.makeText(this, getString(R.string.datetime_after_current_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        val str = DateTimeFormatter.toTimeString(hourOfDay, minute)
        if(DateTimeFormatter.isDateTimeBeforeCurrent("${binding?.dateET?.text} $str")) {
            binding?.timeET?.setText(str)
            invalidateSubmit()
        }
        else {
            Toast.makeText(this, getString(R.string.datetime_after_current_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun invalidateSubmit() {
        binding?.submitBtn?.isEnabled = mainViewModel.dateTime.value != "${binding?.dateET?.text} ${binding?.timeET?.text}"
    }
}