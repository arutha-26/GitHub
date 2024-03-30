@file:Suppress("DEPRECATION")

package com.example.github.ui.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.data.Resource
import com.example.github.databinding.FollowerFragmentBinding
import com.example.github.model.User
import com.example.github.ui.adapter.UserAdapter
import com.kylix.demosubmissionbfaa.util.ViewStateCallback

class FollowerFragment: Fragment(), ViewStateCallback<List<User>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private val followerBinding: FollowerFragmentBinding by viewBinding()
    private val viewModel: FollowerViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.follower_fragment, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userAdapter = UserAdapter()
        followerBinding.rvListUserFollower.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowers(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }

    }

    override fun onSuccess(data: List<User>) {
        userAdapter.setAllData(data)
        followerBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = invisible
            rvListUserFollower.visibility = visible
        }
    }

    override fun onLoading() {
        followerBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = visible
            rvListUserFollower.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followerBinding.apply {
            if (message == null) {
                tvMessage.text = resources.getString(R.string.followers_not_found, username)
                tvMessage.visibility = visible
            } else {
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            followProgressBar.visibility = invisible
            rvListUserFollower.visibility = invisible
        }
    }

}