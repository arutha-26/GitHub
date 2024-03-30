package com.example.github.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.github.data.Repository

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}