package com.example.github.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github.ui.follower.FollowerFragment
import com.example.github.ui.following.FollowingFragment
import com.example.github.util.Constanta.TAB_TITLES

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.getInstance(username)
            1 -> fragment = FollowingFragment.getInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }
}