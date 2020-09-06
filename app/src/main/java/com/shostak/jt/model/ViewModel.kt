package com.shostak.jt.model

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel @ViewModelInject constructor(application: Application, val repository: Repository) :
    AndroidViewModel(application) {

    suspend fun loadJobs() =
        repository.loadJobs()

    fun getItemByPosition(position: Int) = repository.getItem(position)

    fun runSearch(query: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.runTextSearch(query)
    }

    fun getFormattedJson() = repository.getFormattedJson()
}