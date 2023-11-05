package com.example.testtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.model.DateRepository
import com.example.testtask.utils.DateTimeFormatter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: DateRepository): ViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String> = _dateTime

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    init {
        repository.getDate()
            .distinctUntilChanged()
            .onEach {
                if(it == null) {
                    fetchData()
                }
                else {
                    _dateTime.postValue(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchData() {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                repository.fetchDate()
            }
            catch (e: Throwable) {
                _error.postValue(FetchDataException())
            }
            _loading.postValue(false)
        }
    }

    fun updateTime(formattedTime: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            if(DateTimeFormatter.isValidDateTime(formattedTime)) {
                repository.setDate(formattedTime)
            }
            else {
                _error.postValue(InvalidFormatException())
            }
            _loading.postValue(false)
        }
    }

    class InvalidFormatException: Exception()
    class FetchDataException: Exception()
}