@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "DEPRECATION"
)

package com.example.github.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.data.Resource
import com.example.github.databinding.ActivityFavoriteBinding
import com.example.github.model.User
import com.example.github.ui.adapter.UserAdapter
import com.kylix.demosubmissionbfaa.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection"
)
class FavoriteActivity : AppCompatActivity(), ViewStateCallback<List<User>> {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        userAdapter = UserAdapter()

        favoriteBinding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onLoading() {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = visible
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> {
                        it.data?.let { data ->
                            if (data.isEmpty()) {
                                onFailed("There is no favorite user!")
                            } else {
                                onSuccess(data)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onSuccess(data: List<User>) {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = View.INVISIBLE
        }

        if (data.isEmpty()) {
            userAdapter.apply {
                val diffResult = DiffUtil.calculateDiff(UserDiffCallback(listUser, data))
                listUser.clear()
                diffResult.dispatchUpdatesTo(this)
            }
            onFailed("There is no favorite user!")
        } else {
            userAdapter.apply {
                val diffResult = DiffUtil.calculateDiff(UserDiffCallback(listUser, data))
                listUser.clear()
                listUser.addAll(data)
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onFailed(message: String?) {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = View.INVISIBLE
            tvFavoriteError.text = message ?: "There is no favorite user!"
        }

        userAdapter.apply {
            val diffResult = DiffUtil.calculateDiff(UserDiffCallback(listUser, emptyList()))
            listUser.clear()
            diffResult.dispatchUpdatesTo(this)
        }
    }


}