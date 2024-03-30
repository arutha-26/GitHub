package com.example.github.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.github.data.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)

}