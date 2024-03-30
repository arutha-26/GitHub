package com.example.github.ui.follower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.github.data.Repository

class FollowerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getUserFollowers(username: String) = repository.getUserFollowers(username)
}